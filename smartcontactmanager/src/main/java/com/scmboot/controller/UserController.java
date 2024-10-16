package com.scmboot.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scmboot.dao.ContactRepository;
import com.scmboot.dao.UserRepository;
import com.scmboot.entities.Contact;
import com.scmboot.entities.User;
import com.scmboot.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	// common user - data to show on all page
	@ModelAttribute
	public void addCommonData(Model m, Principal p) {
		String userName = p.getName();
		System.out.println("UserName :" + userName);
		User user = this.userRepository.getUserByUserName(userName);
		System.out.println("User : " + user);

		m.addAttribute("user", user);

	}

	// user index
	@GetMapping("/index")
	public String dashboard(Model m, Principal p) {
		String userName = p.getName();
		System.out.println("UserName :" + userName);
		User user = this.userRepository.getUserByUserName(userName);
		m.addAttribute("title", "" + user.getName() + "-SCM-Dashboard");

		return "normal/user_dashboard";
	}

	// open contact form handler
	@GetMapping("/add-contact")
	public String addContactForm(Model m) {
		m.addAttribute("title", "Add-Contact");
		m.addAttribute("contact", new Contact());
		return "normal/user_add_contact_form";
	}

	@PostMapping("/process-contact")
	public String processContactForm(@Valid @ModelAttribute("contact") Contact contact, BindingResult result,
			@RequestParam("file") MultipartFile file, Model model, Principal p, HttpSession session) {

		try {
			if (result.hasErrors()) {
				model.addAttribute("contact", contact);
				System.out.println(result);
				return "normal/user_add_contact_form";
			}

			// Get the user
			String userName = p.getName();
			User user = this.userRepository.getUserByUserName(userName);
			contact.setUser(user);

			// File upload handling
			if (!file.isEmpty()) {

				// Static folder ke andar Images folder ko locate karo
				File saveDir = new ClassPathResource("static/Images").getFile();

				// Unique file name generate karo
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

				// Final file path banao
				Path uploadPath = Paths.get(saveDir.getAbsolutePath() + File.separator + fileName);

				// Agar directory exist nahi karti toh usko create karo
				if (!Files.exists(uploadPath.getParent())) {
					Files.createDirectories(uploadPath.getParent());
				}

				// File ko save karo
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);

				// Contact object mein file ka naam save karo
				contact.setImageUrl(fileName);
			} else {
				contact.setImageUrl("default.png");
			}

			// Save contact
			user.getContacts().add(contact);
			this.userRepository.save(user);
			model.addAttribute("contact", new Contact());
			session.setAttribute("message", new Message("Contact added successfully!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("contact", contact);
			session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
			return "normal/user_add_contact_form";
		}

		return "normal/user_add_contact_form";
	}

	// per page = 5[n]
	// current page = 0 [page]
	// open contact form handler
	@GetMapping("/view-contacts/{page}")
	public String viewContacts(@PathVariable("page") Integer page, Model m, Principal p) {

		m.addAttribute("title", "View-Contacts");

		String userName = p.getName();
		User user = this.userRepository.getUserByUserName(userName);

		if (page < 0) {
			return "redirect:/user/view-contacts/0";
		}

		// pageable object for pagination
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageRequest);

		int totalPages = contacts.getTotalPages();
		System.out.println(page);

		System.out.println("TOTAL PAGE : " + totalPages);
		System.out.println("current page : " + page);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", totalPages);

		return "normal/user_view_contacts";
	}

	// showing specific contact details
	@GetMapping("/{cId}/contact")
	public String showSpecificContactDetail(@PathVariable("cId") Integer cId, Model m, Principal p,
			RedirectAttributes redirectAttributes) {

		String username = p.getName();
		User user = this.userRepository.getUserByUserName(username);

		Optional<Contact> oContact = this.contactRepository.findById(cId);

		// Check if contact exists
		if (oContact.isPresent()) {
			Contact contact = oContact.get();

			// Check if the contact belongs to the current user
			if (user.getId() == contact.getUser().getId()) {
				m.addAttribute("contact", contact);
				m.addAttribute("title", contact.getName());
				return "normal/contact_detail"; // Return contact details page
			} else {
				// Contact does not belong to the user
				redirectAttributes.addFlashAttribute("errorMessage",
						"You do not have permission to view this contact.");
				return "redirect:/user/view-contacts/0"; // Redirect to contacts list
			}
		} else {
			// If contact does not exist in the database
			redirectAttributes.addFlashAttribute("errorMessage", "Contact not found.");
			return "redirect:/user/view-contacts/0"; // Redirect to contacts list
		}
	}

	// delete specific contact details
	@GetMapping("/delete-contact/{cId}")
	public String deleteSpecificContactDetail(@PathVariable("cId") Integer cId, RedirectAttributes redirectAttributes,
			Principal p) {

		System.out.println("CID: " + cId);
		String username = p.getName();
		User user = this.userRepository.getUserByUserName(username);

		// Find the contact by ID to ensure it exists
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);

		if (contactOptional.isPresent()) {

			Contact contact = contactOptional.get();
			// Check if the contact belongs to the current user
			if (user.getId() == contact.getUser().getId()) {
				// Delete the contact
				this.contactRepository.deleteById(cId);
				// Add success message
				redirectAttributes.addFlashAttribute("message", "Contact deleted successfully!");
				return "redirect:/user/view-contacts/0"; // Return contact details page

			} else {
				// Contact does not belong to the user
				redirectAttributes.addFlashAttribute("errorMessage",
						"You do not have permission to delete this contact.");
				return "redirect:/user/view-contacts/0"; // Redirect to contacts list
			}

		} else {
			// If contact is not found, add error message
			redirectAttributes.addFlashAttribute("errorMessage", "Contact not found.");
		}

		// Determine the total number of contacts
		long totalContacts = this.contactRepository.count();
		int totalPages = (int) Math.ceil((double) totalContacts / 5); // Assuming 5 contacts per page

		// Redirect to the appropriate page
		if (totalPages > 0) {
			return "redirect:/user/view-contacts/" + (totalPages - 1); // Redirect to the last page if there are

		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "No contacts available."); // Inform no contacts are

			return "redirect:/user/view-contacts/0"; // Redirect to the first page if no contacts are left
		}

	}

}
