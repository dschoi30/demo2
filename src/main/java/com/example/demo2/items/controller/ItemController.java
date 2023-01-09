package com.example.demo2.items.controller;

import com.example.demo2.items.Item;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.dto.ItemSearchDto;
import com.example.demo2.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/items/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemSaveDto itemSaveDto = itemService.getItemDetail(itemId);
            model.addAttribute("itemSaveDto", itemSaveDto);
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemSaveDto", new ItemSaveDto());
            return "items/itemCreationForm";
        }
        return "items/itemCreationForm";
    }

    @PostMapping(value = "/items/{itemId}")
    public String itemUpdate(@Valid ItemSaveDto itemSaveDto, BindingResult bindingResult,
                             @RequestParam("itemImageFile") List<MultipartFile> itemImageFiles, Model model) throws Exception {
        if(bindingResult.hasErrors()) {
            return "/items/itemCreationForm";
        }
        if(itemImageFiles.get(0).isEmpty() && itemSaveDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력값입니다.");
            return "items/itemCreationForm";
        }

        try {
            itemService.updateItem(itemSaveDto, itemImageFiles);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "items/itemCreationForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemList(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "items/itemList";
    }
}
