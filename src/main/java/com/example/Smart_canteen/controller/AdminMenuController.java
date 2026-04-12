
package com.example.Smart_canteen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Smart_canteen.model.MenuItem;
import com.example.Smart_canteen.repository.MenuRepository;
import com.example.Smart_canteen.service.MenuService;

@Controller

@RequestMapping("/admin/menu")
public class AdminMenuController {
	
	@Autowired
	private MenuRepository menuRepository;

    private final MenuService menuService;

    public AdminMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/add")
    public String showAddMenuForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "add-menu";
    }

    @PostMapping("/add")
    public String addMenuItem(@ModelAttribute MenuItem menuItem) {
        menuService.addMenuItem(menuItem);
        return "redirect:/admin/menu/list";  
    }

    @GetMapping("/list")
    public String listMenu(Model model) {
        model.addAttribute("menuItems", menuService.getAllMenuItems());
        return "admin-menu-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return "redirect:/admin/menu/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditMenuForm(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuRepository.findById(id).orElse(null);
        if (menuItem != null) {
            model.addAttribute("menuItem", menuItem);
            return "edit-menu";
        }
        return "redirect:/admin/menu/list";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(@PathVariable Long id, @ModelAttribute MenuItem menuItem) {
        menuItem.setId(id);
        menuService.updateMenuItem(menuItem);
        return "redirect:/admin/menu/list";
    }
    
    @PostMapping("/admin/menu/add")
    public String addMenu(MenuItem menuItem) {

        menuRepository.save(menuItem);

        return "redirect:/admin/menu";
    }
    
    
}









//package com.example.Smart_canteen.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.Smart_canteen.model.MenuItem;
//import com.example.Smart_canteen.service.MenuService;
//
//@Controller
//@RequestMapping("/admin/menu")
//public class AdminMenuController {
//
//    private final MenuService menuService;
//
//    public AdminMenuController(MenuService menuService) {
//        this.menuService = menuService;
//    }
//
//    // Add menu page
//    @GetMapping("/add")
//    public String showAddMenuForm(Model model) {
//        model.addAttribute("menuItem", new MenuItem());
//        return "add-menu";
//    }
//
//    // Save menu item
//    @PostMapping("/add")
//    public String addMenuItem(@ModelAttribute MenuItem menuItem) {
//        menuService.addMenuItem(menuItem);
//        return "redirect:/admin/menu/list";   // ✅ CORRECT
//    }
//
//    // Admin menu list (ONLY ONE)
//    @GetMapping("/list")
//    public String listMenu(Model model) {
//        model.addAttribute("menuItems", menuService.getAllMenuItems());
//        return "admin-menu-list";
//    }
//
//    // Delete item
//    @GetMapping("/delete/{id}")
//    public String deleteMenuItem(@PathVariable Long id) {
//        menuService.deleteMenuItem(id);
//        return "redirect:/admin/menu/list";
//    }
//
//}
