package com.example.Smart_canteen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="menu_items")
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	@Column(name = "food_name", nullable = false)
	private String foodName;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String category;
	@Column(name = "available", nullable = false)
	private boolean Available;
	
	private String image;
	
//	added when the project is being updated
	
	
	public MenuItem() {
		
	}

	public MenuItem(Long id, String foodName, double price, String category, boolean available) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.price = price;
		this.category = category;
		Available = available;
	}

	public MenuItem(Long id, String foodName, double price, String category, boolean available, String image) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.price = price;
		this.category = category;
		Available = available;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isAvailable() {
		return Available;
	}

	public void setAvailable(boolean available) {
		Available = available;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
