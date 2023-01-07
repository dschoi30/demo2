package com.example.demo2.items.controller;

import com.example.demo2.items.dto.ItemSaveDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/items/new")
    public String itemSaveForm(Model model) {
        model.addAttribute("itemSaveDto", new ItemSaveDto());
        return "items/itemCreationForm";
    }
}
