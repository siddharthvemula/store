package com.sid.store.controller;

import com.sid.store.model.Item;
import com.sid.store.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("allItems", allItems);
        return "home/home";
    }
}