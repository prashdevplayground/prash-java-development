package com.example.inventory.controller;

import com.example.inventory.dto.InventoryDtos.*;
import com.example.inventory.model.InventoryTransaction;
import com.example.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inv;
    public InventoryController(InventoryService inv){ this.inv = inv; }

    @PostMapping("/apply")
    public ResponseEntity<TxnResponse> apply(@RequestBody TxnRequest req){
        return ResponseEntity.ok(inv.apply(req));
    }

    @GetMapping("/product/{productId}")
    public List<InventoryTransaction> history(@PathVariable Long productId){
        return inv.forProduct(productId);
    }
}
