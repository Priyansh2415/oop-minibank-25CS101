package com.shop;

import java.util.LinkedHashMap;
import java.util.Map;

public class Rules {
    // every rule is a lambda stored in a map the user chooses from
    public static Map<String, DiscountRule> all() {
        Map<String, DiscountRule> rules = new LinkedHashMap<>();
        rules.put("1", price -> price * 0.90);                        // 10% off
        rules.put("2", price -> price - 100);                         // flat Rs 100 off
        rules.put("3", price -> price > 1000 ? price * 0.80 : price); // 20% off above Rs 1000
        return rules;
    }
}
