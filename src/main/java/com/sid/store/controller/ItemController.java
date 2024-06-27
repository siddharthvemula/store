package com.sid.store.controller;
import com.sid.store.model.Item;
import com.sid.store.model.Temp;
import com.sid.store.persistence.ItemRepository;
import com.sid.store.persistence.TempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/items")
    public String items(Model model) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("allItems", allItems);
        return "item/items";
    }

    @GetMapping("/item")
    public String navigateItem(@RequestParam(name="id", required=false) Long id, Model model) {
        Item item = new Item();
        if (id != null) {
            item = itemRepository.findById(id).get();
        }
        model.addAttribute("item", item);
        return "item/itemForm";
    }

    @PostMapping("/item")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "redirect:items";
    }
//
//    @DeleteMapping("/temp")
//    public String deleteTemp(@RequestParam(name="id", required=true) Long id) {
//        tempRepository.deleteById(id);
//        return "redirect:temps";
//    }
}