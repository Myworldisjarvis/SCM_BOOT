package com.scmboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String name;
    
    @NotBlank(message = "Second name is required")
    @Size(min = 2, max = 30, message = "Second name must be between 2 and 30 characters")
    private String secondName;
    
    private String work;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;
    
    private String file;
    
    @Size(max = 50000, message = "Description can't exceed 50000 characters")
    private String description;
    
    @ManyToOne()
    private User user;


    

    public int getcId() {
		return cId;
	}




	public void setcId(int cId) {
		this.cId = cId;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getSecondName() {
		return secondName;
	}




	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}




	public String getWork() {
		return work;
	}




	public void setWork(String work) {
		this.work = work;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getImageUrl() {
		return file;
	}




	public void setImageUrl(String file) {
		this.file = file;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	@Override
    public String toString() {
        return "Contact [cId=" + cId + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", email="
                + email + ", phone=" + phone + ", imageUrl=" + file + ", description=" + description + "]";
    }
}
