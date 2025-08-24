package com.example.inventory.service;

import com.example.inventory.dto.InventoryDtos.*;
import com.example.inventory.model.InventoryTransaction;
import com.example.inventory.model.Product;
import com.example.inventory.repo.InventoryTransactionRepository;
import com.example.inventory.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryTransactionRepository txns;
    private final ProductRepository products;

    public InventoryService(InventoryTransactionRepository txns, ProductRepository products){
        this.txns = txns; this.products = products;
    }

    @Transactional
    public TxnResponse apply(TxnRequest req){
        Product p = products.findById(req.productId).orElseThrow();
        p.setQuantity(p.getQuantity() + req.delta);
        products.save(p);
        InventoryTransaction t = txns.save(InventoryTransaction.builder()
                .product(p).delta(req.delta).at(Instant.now()).build());
        TxnResponse r = new TxnResponse();
        r.id = t.getId(); r.productId = p.getId(); r.delta = t.getDelta(); r.at = t.getAt();
        return r;
    }

    public List<InventoryTransaction> forProduct(Long productId){
        Product p = products.findById(productId).orElseThrow();
        return txns.findByProduct(p);
    }
}
