package com.example.Smart_canteen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Smart_canteen.model.MenuItem;
import com.example.Smart_canteen.repository.MenuRepository;

@Service
public class MenuService {
	
	private final MenuRepository menuRepository;
	
	// constructor injection
	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}
	
	//add
	public MenuItem addMenuItem(MenuItem item) {
		return menuRepository.save(item);
	}
	
	public MenuItem getMenuItemById(Long id) {
	    return menuRepository.findById(id).orElse(null);
	}


//	get
	public List<MenuItem>getAllMenuItems(){
		return menuRepository.findAll();
	}
	//Delete
	public void deleteMenuItem(Long id) {
		menuRepository.deleteById(id);
	}

	// Update
	public MenuItem updateMenuItem(MenuItem item) {
		return menuRepository.save(item);
	}
}
