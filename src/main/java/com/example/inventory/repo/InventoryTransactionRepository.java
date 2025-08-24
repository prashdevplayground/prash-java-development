package com.example.inventory.repo;

import com.example.inventory.model.InventoryTransaction;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    List<InventoryTransaction> findByProduct(Product product);

    @Query(value = "SELECT p.name, SUM(t.delta) as total_delta FROM inventory_transaction t " +
            "JOIN product p ON p.id = t.product_id WHERE t.product_id = :productId GROUP BY p.name",
            nativeQuery = true)
    List<Object[]> sumByProduct(@Param("productId") Long productId);
}
