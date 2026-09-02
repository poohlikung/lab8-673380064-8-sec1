package com.example.lab8.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;
    private String category;
    private String brand;
    private Integer stock;
    private Double price;
    private String discountType = "NONE";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private ProductDetail detail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Transient
    private Double discountedPrice;

    public void setDetail(ProductDetail detail) {
        this.detail = detail;

        if (detail != null) {
            detail.setProduct(this);
        }
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews == null
                ? new ArrayList<>()
                : reviews;

        for (Review review : this.reviews) {
            review.setProduct(this);
        }
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public Double getDiscountedPrice() {
        return discountedPrice == null ? price : discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
