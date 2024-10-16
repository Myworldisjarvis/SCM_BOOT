package com.scmboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.scmboot.dao.UserRepository;
import com.scmboot.entities.User;

public class UserDetaillsServiceImple implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database 
		 User user = userRepository.getUserByUserName(username);
		 
		 if(user==null) {
			 throw new UsernameNotFoundException("Could not Found !!");
		 }
		 
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return  customUserDetails;
	}

}
