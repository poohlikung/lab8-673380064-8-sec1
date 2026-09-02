package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

@Component
public class DiscountContext {

    private final NoDiscountStrategy noDiscountStrategy;
    private final MemberDiscountStrategy memberDiscountStrategy;
    private final SeasonalSaleStrategy seasonalSaleStrategy;

    public DiscountContext(
            NoDiscountStrategy noDiscountStrategy,
            MemberDiscountStrategy memberDiscountStrategy,
            SeasonalSaleStrategy seasonalSaleStrategy) {

        this.noDiscountStrategy = noDiscountStrategy;
        this.memberDiscountStrategy = memberDiscountStrategy;
        this.seasonalSaleStrategy = seasonalSaleStrategy;
    }

    public double calculate(String discountType, double price) {

        if ("MEMBER".equalsIgnoreCase(discountType)) {
            return memberDiscountStrategy.calculate(price);
        }

        if ("SEASONAL".equalsIgnoreCase(discountType)) {
            return seasonalSaleStrategy.calculate(price);
        }

        return noDiscountStrategy.calculate(price);
    }
}