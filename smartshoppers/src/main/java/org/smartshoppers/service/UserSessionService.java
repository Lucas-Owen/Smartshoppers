package org.smartshoppers.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.smartshoppers.dao.ShoppingItemRepo;
import org.smartshoppers.dao.StoreRepo;
import org.smartshoppers.dao.UserRepo;
import org.smartshoppers.model.ShoppingItem;
import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.notification.Notification;


@Service
public class UserSessionService {
	private final StoreRepo storeRepo;
	private final UserRepo userRepo;
	private final ShoppingItemRepo shoppingItemRepo;
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public UserSessionService(@Autowired UserRepo userRepo, 
			@Autowired StoreRepo shopRepo,
			@Autowired ShoppingItemRepo shoppingItemRepo) {
		this.storeRepo = shopRepo;
		this.userRepo = userRepo;
		this.shoppingItemRepo = shoppingItemRepo;
	}
	
	// Save operations
    public Store addStore(Store store)
    {
        return storeRepo.save(store);
    }
    
    public ShoppingItem addShoppingItem(ShoppingItem shoppingItem)
    {
    	Optional<ShoppingItem> item = shoppingItemRepo.findById(shoppingItem.getName());
    	
    	if(item.isPresent()) {
    		Notification.show("Item Already Exists");
    		return shoppingItem;
    	}
    	Notification.show("Adding new item - " + shoppingItem.getName());
        return shoppingItemRepo.save(shoppingItem);
    }
    
    public boolean addUser(User user)
    {
    	User u = findUserByUserName(user.getUserName());
    	if(u != null) {
    		Notification.show("User Already Exists");
    		return false;
    	}
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }
    
  
    // Read operations
    public User findUserByUserName(String userName) {
    	return userRepo.findByUserName(userName);
    }
    public List<Store> findAllStores() {
        return (List<Store>) storeRepo.findAll();
    }
    public List<User> findAllUsers() {
        return (List<User>) userRepo.findAll();
    }
    public List<ShoppingItem> findAllShoppingItems() {
        return (List<ShoppingItem>) shoppingItemRepo.findAll();
    }
    public List<ShoppingItem> findAllShoppingItemsInStore(String storeId) {
    	Optional<Store> store = storeRepo.findById(storeId);
    	if(store.isPresent()) {
    		return shoppingItemRepo.findAllByStore(storeId);
    	}
    	return null;
    }

    
    public Store findStoreByManager(User manager) {
    	manager = userRepo.findByUserName(manager.getUserName());
    	return manager.getStore();
    }
  
    // Update operation
    public Store updateStore(Store store)   {
    	Optional<Store> item = storeRepo.findById(store.getId());
        if(item.isPresent()) {
        	return storeRepo.save(store);
        }
  
        return store;
    }
    
    public ShoppingItem updateShoppingItem(ShoppingItem shoppingItem)   {
        Optional<ShoppingItem> item = shoppingItemRepo.findById(shoppingItem.getName());
        if(item.isPresent()) {
        	return shoppingItemRepo.save(shoppingItem);
        }


        return shoppingItem;
    }
    
  
    // Delete operation
    public void deleteStoreById(Store store)    {
        storeRepo.deleteById(store.getId());
    }
    
    public boolean deleteUserById(User user)    {
    	try {
    		userRepo.updateStore(null, user.getUserName());
	        userRepo.deleteById(user.getUserName());

	        return true;
    	}
    	catch(Throwable t) {
    		return false;
    	}
    }
    
    public void deleteShoppingItemById(ShoppingItem shoppingItem)    {
        shoppingItemRepo.deleteById(shoppingItem.getId());
    }
    
    // Count shops
    public Long count() {
    	return storeRepo.count();
    }

	public Optional<Store> findStoreById(String store) {
		
		return storeRepo.findById(store);
	}

	public void updateUser(User currentUser) {
		
		
		currentUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
		userRepo.update(currentUser.getUserName(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getStore(), currentUser.getPassword(), currentUser.getRole());
	}
	
	public void adminUpdateUser(User user) {
		userRepo.update(user.getUserName(), user.getFirstName(), user.getLastName(), user.getStore(), user.getPassword(), user.getRole());
	}

	public void verifyUser(User user) throws Throwable {
		User verified = userRepo.findByUserName(user.getUserName());
		
		if(!passwordEncoder.matches(user.getPassword(), verified.getPassword()))
			throw new Throwable();
	}
	
	public boolean userNameExists(User user) {
		return userRepo.findByUserName(user.getUserName()) != null;
	}
	
	public boolean userNameExists(String userName) {
		return userRepo.findByUserName(userName) != null;
	}
	
	public String getCurrentUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public User getCurrentUser() {
		return userRepo.findByUserName(getCurrentUserName());
	}
	
	
	//ShoppingItems
	public void addShoppingItemToCurrentUserShoppingList(ShoppingItem shoppingItem) {
		User user = getCurrentUser();
		
		Set<ShoppingItem> shoppingList = user.getShoppingList();
		shoppingList.add(shoppingItem);
		user.setShoppingList(shoppingList);
		
		Set<User> users = shoppingItem.getUsers();
		users.add(user);
		shoppingItem.setUsers(users);
		
		shoppingItemRepo.save(shoppingItem);
		userRepo.save(user);
		
	}


	public Collection<User> findAllManagers() {
		
		
		return userRepo.findAllByRole("MANAGER");
	}

	
	public User findManagerByStore(Store store) {
		
		return userRepo.findByStore(store);
	}

	
	public Collection<Store> findAllStoresWithoutManager() {
		
		return storeRepo.findAllWithoutManager();
	}

	public void deleteStoreWithManager(User manager) {
		Store store = manager.getStore();
		userRepo.updateStore(null, manager.getUserName());
		storeRepo.delete(store);
	}

	public void addStore(Store store, User manager) {

		storeRepo.save(store);
		userRepo.updateStore(store, manager.getUserName());
		
	}

}
