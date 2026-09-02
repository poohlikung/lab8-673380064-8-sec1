package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

@Component
public class MemberDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculate(double price) {
        return price * 0.90;
    }
}