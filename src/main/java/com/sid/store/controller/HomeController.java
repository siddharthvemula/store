package com.sid.store.controller;

import com.sid.store.model.Cart;
import com.sid.store.model.Item;
import com.sid.store.model.Temp;
import com.sid.store.model.User;
import com.sid.store.persistence.CartRepository;
import com.sid.store.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("allItems", allItems);
        return "home/home";
    }

    @GetMapping("/product")
    public String product(@RequestParam(name="id", required=false) Long id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        return "home/product";
    }
    @PostMapping("/cart")
    public String addToCart(@AuthenticationPrincipal User user, @ModelAttribute Cart cart, Model model) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //cart.setUserId((((User)authentication.getPrincipal()).getId()));
        cart.setUserId(user.getId());
        cartRepository.save(cart);
        return "redirect:/";
    }
}


