package com.example.demo2.items.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.dto.ItemImageDto;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.dto.ItemSearchDto;
import com.example.demo2.items.dto.MainItemDto;
import com.example.demo2.items.repository.ItemImageRepository;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.reviews.Review;
import com.example.demo2.reviews.ReviewImage;
import com.example.demo2.reviews.dto.ReviewDto;
import com.example.demo2.reviews.dto.ReviewImageDto;
import com.example.demo2.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(ItemSaveDto itemSaveDto, List<MultipartFile> itemImageFiles) throws Exception {
        Item item = itemSaveDto.createItem();
        itemRepository.save(item);

        for (int i = 0; i < itemImageFiles.size(); i++) {
            ItemImage itemImage = new ItemImage();
            itemImage.modifyItem(item);

            if (i == 0) {
                itemImage.isRepImage();
            }
            itemImageService.saveItemImage(itemImage, itemImageFiles.get(i));
        }
        return item.getId();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????????."));
    }
    public ItemSaveDto getItemDetail(Long itemId) {
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImageDto> itemImageDtos = new ArrayList<>();
        for (ItemImage itemImage : itemImages) {
            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtos.add(itemImageDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemSaveDto itemSaveDto = ItemSaveDto.of(item);
        itemSaveDto.setItemImageDtos(itemImageDtos);

        return itemSaveDto;
    }
    public ItemSaveDto getItemDetailWithReviews(Long itemId, Pageable pageable) {
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtos = new ArrayList<>();
        for (ItemImage itemImage : itemImages) {
            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtos.add(itemImageDto);
        }
        List<Review> reviews = reviewRepository.findByItemIdOrderByIdDesc(itemId, pageable);
        Long totalCount = reviewRepository.countReviewByItemId(itemId);

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto(review);
            List<ReviewImage> reviewImages = review.getReviewImages();
            for (ReviewImage reviewImage : reviewImages) {
                ReviewImageDto reviewImageDto = ReviewImageDto.of(reviewImage);
                reviewDto.addReviewImageDto(reviewImageDto);
            }
            reviewDtos.add(reviewDto);
        }
        Page<ReviewDto> reviewDtosPage = new PageImpl<>(reviewDtos, pageable, totalCount);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemSaveDto itemSaveDto = ItemSaveDto.of(item);
        itemSaveDto.setItemImageDtos(itemImageDtos);
        itemSaveDto.setReviewDtosPage(reviewDtosPage);
        return itemSaveDto;
    }

    @Transactional
    public Long updateItem(ItemSaveDto itemSaveDto, List<MultipartFile> itemImageFiles) throws Exception {
        Item item = itemRepository.findById(itemSaveDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemSaveDto);

        List<Long> itemImageIds = itemSaveDto.getItemImageIds();

        for (int i = 0; i < itemImageFiles.size(); i++) {
            itemImageService.updateItemImage(itemImageIds.get(i), itemImageFiles.get(i));
        }
        return item.getId();
    }

    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    public Page<MainItemDto> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getItemPage(itemSearchDto, pageable);
    }
}
