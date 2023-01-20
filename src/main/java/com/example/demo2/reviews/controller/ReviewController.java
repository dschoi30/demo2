package com.example.demo2.reviews.controller;

import com.example.demo2.items.service.ItemService;
import com.example.demo2.members.service.MemberService;
import com.example.demo2.reviews.Review;
import com.example.demo2.reviews.dto.ReviewDto;
import com.example.demo2.reviews.dto.ReviewSaveDto;
import com.example.demo2.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final ReviewService reviewService;

    @GetMapping(value = "/review/{itemId}/new")
    public String reviewSaveForm(@PathVariable("itemId") Long itemId, Model model) {

        model.addAttribute("reviewSaveDto", new ReviewSaveDto(itemId));
        return "reviews/reviewCreationForm";
    }

    @PostMapping(value = "/review/{itemId}/new")
    public String reviewNew(@Valid ReviewSaveDto reviewSaveDto, BindingResult bindingResult, Principal principal,
                            Model model, @RequestParam("reviewImageFile") List<MultipartFile> reviewImageFiles) {
        if(bindingResult.hasErrors()) {
            return "reviews/reviewCreationForm";
        }

        try {
        reviewService.saveReview(reviewSaveDto, reviewImageFiles, principal.getName());

        } catch (Exception e) {
            model.addAttribute("errorMessage", "리뷰 등록 중 에러가 발생하였습니다.");
            return "/reviews/reviewCreationForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/review/{itemId}/update")
    public String reviewUpdate() {
        return "redirect:/";
    }

    @GetMapping(value = {"/reviews", "/reviews/{page}"})
    public String reviewHistory(@PathVariable("page")Optional<Integer> page, Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<ReviewDto> reviews = reviewService.getReviewPage(principal.getName(), pageable);

        model.addAttribute("reviews", reviews);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "/reviews/reviewHistory";
    }
}
