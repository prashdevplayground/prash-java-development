package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repo.InventoryTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AIService {
    private final InventoryTransactionRepository txns;

    public AIService(InventoryTransactionRepository txns){
        this.txns = txns;
    }

    /**
     * Simple AI-ish heuristic:
     * - Look at last N transactions and estimate daily demand (EMA).
     * - Recommend reorder if projected 14-day demand exceeds current quantity.
     */
    public Map<String, Object> reorderRecommendation(Product product){
        var history = txns.findByProduct(product);
        // Use only negative deltas (sales) as demand
        List<Integer> sales = new ArrayList<>();
        for (var t: history){
            if (t.getDelta() < 0) sales.add(-t.getDelta());
        }
        double ema = 0.0;
        double alpha = 0.4; // smoothing
        for (Integer s : sales){
            ema = alpha * s + (1 - alpha) * ema;
        }
        double projected14 = ema * 14 / 1.0; // naive daily EMA * 14 days
        boolean shouldReorder = projected14 > product.getQuantity();
        int suggestedQty = shouldReorder ? (int)Math.ceil(projected14 - product.getQuantity()) : 0;

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("productId", product.getId());
        out.put("productName", product.getName());
        out.put("currentQuantity", product.getQuantity());
        out.put("estimatedDailyDemand", ema);
        out.put("projected14DayDemand", projected14);
        out.put("shouldReorder", shouldReorder);
        out.put("suggestedReorderQuantity", suggestedQty);
        return out;
    }
}
