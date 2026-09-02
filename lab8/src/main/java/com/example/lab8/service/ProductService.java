package com.example.lab8.service;

import com.example.lab8.model.Product;
import com.example.lab8.model.ProductDetail;
import com.example.lab8.model.Review;
import com.example.lab8.repository.ProductRepository;
import com.example.lab8.strategy.DiscountContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountContext discountContext;

    public ProductService(
            ProductRepository productRepository,
            DiscountContext discountContext) {

        this.productRepository = productRepository;
        this.discountContext = discountContext;
    }

    public List<Product> findAll() {

        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            if (product.getPrice() != null) {
                double discountedPrice = discountContext.calculate(
                        product.getDiscountType(),
                        product.getPrice());

                product.setDiscountedPrice(discountedPrice);
            }
        }

        return products;
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบสินค้าหมายเลข " + id));
    }

    @Transactional
    public Product save(Product product) {

        prepareRelations(product);

        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product input) {

        Product existing = findById(id);

        existing.setName(input.getName());
        existing.setCategory(input.getCategory());
        existing.setBrand(input.getBrand());
        existing.setStock(input.getStock());
        existing.setPrice(input.getPrice());
        existing.setDiscountType(input.getDiscountType());

        if (existing.getDetail() == null) {
            existing.setDetail(input.getDetail());
        } else if (input.getDetail() != null) {
            ProductDetail oldDetail = existing.getDetail();
            ProductDetail newDetail = input.getDetail();

            oldDetail.setDescription(newDetail.getDescription());
            oldDetail.setWarranty(newDetail.getWarranty());
            oldDetail.setWeight(newDetail.getWeight());
            oldDetail.setDimensions(newDetail.getDimensions());
            oldDetail.setManufacturedCountry(
                    newDetail.getManufacturedCountry());
        }

        return productRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private void prepareRelations(Product product) {

        if (product.getDiscountType() == null) {
            product.setDiscountType("NONE");
        }

        if (product.getDetail() == null) {
            product.setDetail(new ProductDetail());
        } else {
            product.getDetail().setProduct(product);
        }

        product.getReviews().removeIf(review -> isBlank(review.getReviewer())
                && isBlank(review.getComment()));

        for (Review review : product.getReviews()) {
            review.setProduct(product);

            if (review.getReviewDate() == null) {
                review.setReviewDate(LocalDate.now());
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}