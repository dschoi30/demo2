package com.example.demo2.items.controller;

import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ItemController {

    @Autowired ItemService itemService;

    @GetMapping("/items/new")
    public String itemSaveForm(Model model) {
        model.addAttribute("itemSaveDto", new ItemSaveDto());
        return "items/itemCreationForm";
    }

    @PostMapping("/items/new")
    public String itemNew(@Valid ItemSaveDto itemSaveDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImageFile")List<MultipartFile> itemImageFiles) {

        if(bindingResult.hasErrors()) {
            return "items/itemCreationForm";
        }

        if(itemImageFiles.get(0).isEmpty() && itemSaveDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니다.");
            return "items/itemCreationForm";
        }

        try {
            itemService.save(itemSaveDto, itemImageFiles);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
        }
    return "redirect:/";
    }
}
