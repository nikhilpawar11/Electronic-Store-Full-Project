package com.nikhil.electronic.store.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Products_Table")
public class Product {
	
	@Id
	@Column(name = "Product_Id")
	private String id;
	
	@Column(name = "Product_Title")
	private String title;

	@Column(name = "Product_Description", length = 10000)
	private String discription;
	
	@Column(name = "Product_Price")
	private int price;
	
	@Column(name = "Product_Discounted_Price")
	private int discountedPrice;
	
	@Column(name = "Product_Added_Date")
	private Date addedDate;
	
	@Column(name = "Product_Image")
	private String productImageUrl;
	
	@Column(name = "Product_Quantity")
	private int quantity;
	
	@Column(name = "Product_Live")
	private boolean live;
	
	@Column(name = "Product_Stock")
	private boolean stock;
	
	// means for many product have only one category.
	// fetch = eager means when we fetch product so that time category also fetch.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Category_Id") // here we change join column name as category_id in product table in database.
	private Category category;

}
