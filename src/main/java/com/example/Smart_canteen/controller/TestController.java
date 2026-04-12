package com.example.Smart_canteen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_canteen.repository.MenuRepository;
import com.example.Smart_canteen.service.MenuService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @GetMapping("/test/menu-count")
    public Map<String, Object> testMenuCount() {
        Map<String, Object> response = new HashMap<>();
        long count = menuRepository.count();
        response.put("menu_item_count", count);
        response.put("all_items", menuService.getAllMenuItems());
        return response;
    }
}
