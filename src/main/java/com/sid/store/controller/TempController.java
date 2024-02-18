package com.sid.store.controller;
import com.sid.store.model.Temp;
import com.sid.store.persistence.TempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class TempController {
    @Autowired
    TempRepository tempRepository;

    @GetMapping("/temps")
    public String temps(Model model) {
        Iterable<Temp> allTemps = tempRepository.findAll();
        model.addAttribute("allTemps", allTemps);
        return "temp/temps";
    }

    @GetMapping("/temp")
    public String navigateTemp(@RequestParam(name="id", required=false) Long id, Model model) {
        Temp temp = new Temp();
        if (id != null) {
            temp = tempRepository.findById(id).get();
        }
        model.addAttribute("temp", temp);
        return "temp/tempForm";
    }
    @PostMapping("/temp")
    public String createTemp(@ModelAttribute Temp temp, Model model) {
        tempRepository.save(temp);
        model.addAttribute("temp", temp);
        return "redirect:temps";
    }

    @DeleteMapping("/temp")
    public String deleteTemp(@RequestParam(name="id", required=true) Long id) {
        tempRepository.deleteById(id);
        return "redirect:temps";
    }
}