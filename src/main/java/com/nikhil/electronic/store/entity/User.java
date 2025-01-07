package com.nikhil.electronic.store.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Users_Table")
public class User {
	
	@Id
	@Column(name = "User_Id")
	private String id;
	
	@Column(name = "User_Name")
	private String name;
	
	@Column(name = "User_Email", unique = true)
	private String email;
	
	@Column(name = "User_Password")
	private String password;
	
	@Column(name = "User_Gender")
	private String gender;
	
	@Column(name = "User_Image_Name")
	private String imageName;
	
	@Column(name = "User_Role")
	private Role role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Order> orders = new ArrayList<>();

}
