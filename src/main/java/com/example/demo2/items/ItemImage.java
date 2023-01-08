package com.example.demo2.items;

import com.example.demo2.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ItemImage extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String imageName;
    private String originalImageName;
    private String imageUrl;
    private boolean isRepImage = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImage(String imageName, String originalImageName, String imageUrl) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
    }

    public void modifyItem(Item item) {
        this.item = item;
    }

    public void isRepImage() {
        this.isRepImage = true;
    }
}
