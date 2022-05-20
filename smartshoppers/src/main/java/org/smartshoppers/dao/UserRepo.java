package org.smartshoppers.dao;

import java.util.Collection;

import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, String> {
	User findByUserName(String userName);
	
	Collection<User> findAllByRole(String role);

	User findByStore(Store store);
	
	@Modifying
	@Query("UPDATE User u SET u.store = :store WHERE u.userName = :user")
	void updateStore(@Param("store") Store store, @Param("user") String manager);
	
	@Modifying
	@Query("UPDATE User u SET u.password=:password, u.firstName=:firstname, u.lastName=:lastname, u.store=:store, u.role=:role WHERE u.userName=:username")
	void update(@Param("username") String username,
			@Param("firstname") String firstName,
			@Param("lastname") String lastName,
			@Param("store") Store store,
			@Param("password") String password,
			@Param("role") String role);
}
