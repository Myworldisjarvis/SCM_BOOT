package com.scmboot.controller;

import java.lang.annotation.Retention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scmboot.dao.UserRepository;
import com.scmboot.entities.User;
import com.scmboot.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

public class HomeController {

	@Autowired
	private BCryptPasswordEncoder  passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model m) {

		m.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model m) {

		m.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signUp(Model m) {

		m.addAttribute("title", "Register - Smart Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}

	// this handler for register user
	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute("user") User user,BindingResult res,
			@RequestParam(value = "agreement", defaultValue = "false") Boolean agreement , Model m,HttpSession session) {

		try {
			if(!agreement) {
				System.out.println("You have not check agreed terms and condition..");
				throw new Exception("You have not check agreed terms and condition..");
			}
		
			if(res.hasErrors()) {
				System.out.println("Error"+res.toString());
				m.addAttribute("user",user);
				return "signup";
			}
			user.setUser_type("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			
		System.out.println("agreement "+agreement);
		System.out.println("user "+user); 

		User result = this.userRepository.save(user);
		
		m.addAttribute("user", new User());
		 
		session.setAttribute("message", new Message("Successfully Registered !! ", "alert-success"));
		
		
        
		return "signup";

		
			
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user",user);
			session.setAttribute("message", new Message("Somting Went Wrong !! "+e.getMessage(), "alert-danger"));
			return "signup";

		}
	}
	
	//login handler
	@GetMapping("/signin")
	public String customLogin(Model m ) {
		m.addAttribute("title","Login Page");
		
		return "login";
	}
	
	
	}
