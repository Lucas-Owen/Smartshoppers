package org.smartshoppers.service;

import org.smartshoppers.dao.UserRepo;
import org.smartshoppers.model.User;
import org.smartshoppers.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepo userRepo;
	
	public UserDetailsServiceImpl(@Autowired UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User userAccount = userRepo.findByUserName(userName);
		if(userAccount == null) throw new UsernameNotFoundException(userName + " Not found!");
		
		return new CustomUserDetails(userAccount);
	}

}
