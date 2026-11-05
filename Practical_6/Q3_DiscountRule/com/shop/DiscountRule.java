package com.shop;

@FunctionalInterface
public interface DiscountRule {
    double apply(double price);
}
