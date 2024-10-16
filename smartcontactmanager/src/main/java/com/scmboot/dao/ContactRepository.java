package com.scmboot.dao;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scmboot.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	//custom method to fatch logedIn user contact 
	@Query("from Contact as c where c.user.id =:userId")
	//currentpage = page
	//Contact per page = 5 
	public Page<Contact> findContactsByUser(@Param("userId") int userID,Pageable pageable); 

	//pagination...
	 
	 
	
}
