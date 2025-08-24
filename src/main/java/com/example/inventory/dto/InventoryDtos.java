package com.example.inventory.dto;

import java.time.Instant;

public class InventoryDtos {
    public static class TxnRequest { public Long productId; public Integer delta; }
    public static class TxnResponse { public Long id; public Long productId; public Integer delta; public Instant at; }
}
