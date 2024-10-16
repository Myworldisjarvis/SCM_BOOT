package com.scmboot.entities;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
 	private int id;
	
	@NotNull
	@NotBlank(message = "Name Field is required !!")
	@Size(min = 2,max = 20, message = "Min 2 & max 20 characters are required !!")
	private String name;
	@NotBlank(message = "Email must requreid !!")
	@Email
	@Column(unique = true)
	private String email;
	@NotNull
	@NotBlank(message = "Password must be Strong contain special symbol !!")
	@Size(min = 6, message = "Min 8 & max 20 characters are required !!")
	private String password;
	private String user_type;
	private boolean enabled;
	private String imageUrl;
	@Column(length = 500)
	@NotNull
	private String uDetails;
	
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY,mappedBy = "user")
	private List<Contact> contacts = new ArrayList<>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getuDetails() {
		return uDetails;
	}
	public void setuDetails(String uDetails) {
		this.uDetails = uDetails;
	}
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	public User(String name, String email, String password, String user_type, boolean enabled, String imageUrl,
			String uDetails) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.user_type = user_type;
		this.enabled = enabled;
		this.imageUrl = imageUrl;
		this.uDetails = uDetails;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", user_type="
				+ user_type + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", uDetails=" + uDetails + "]";
	}

	
	
}
