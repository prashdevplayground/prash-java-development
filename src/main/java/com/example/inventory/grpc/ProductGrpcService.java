package com.example.inventory.grpc;

import com.example.inventory.dto.ProductDtos;
import com.example.inventory.service.ProductService;
import com.example.inventory.model.Product;
import jakarta.persistence.Id;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceGrpcImplBase {
    private final com.example.inventory.service.ProductService svc;

    public ProductGrpcService(ProductService svc){ this.svc = svc; }

    @Override
    public void create(ProductDtos.ProductRequest request, StreamObserver<ProductDtos.ProductResponse> responseObserver) {
        var req = new com.example.inventory.dto.ProductDtos.ProductRequest();
        req.name = request.getName();
        req.categoryId = request.getCategoryId();
        req.price = java.math.BigDecimal.valueOf(request.getPrice());
        req.quantity = request.getQuantity();
        var res = svc.create(req);
        ProductDtos.ProductResponse out = ProductResponse.newBuilder()
                .setId(res.id)
                .setName(res.name)
                .setCategory(res.category)
                .setPrice(res.price == null ? 0.0 : res.price.doubleValue())
                .setQuantity(res.quantity == null ? 0 : res.quantity)
                .build();
        responseObserver.onNext(out);
        responseObserver.onCompleted();
    }

    @Override
    public void get(Id request, StreamObserver<ProductDtos.ProductResponse> responseObserver) {
        Product p = svc.get(request.getId());
        var out = ProductResponse.newBuilder()
                .setId(p.getId())
                .setName(p.getName())
                .setCategory(p.getCategory().getName())
                .setPrice(p.getPrice() == null ? 0.0 : p.getPrice().doubleValue())
                .setQuantity(p.getQuantity())
                .build();
        responseObserver.onNext(out);
        responseObserver.onCompleted();
    }

    @Override
    public void search(SearchQuery request, StreamObserver<ProductList> responseObserver) {
        var list = svc.search(request.getQ());
        var builder = ProductList.newBuilder();
        for (var p : list){
            builder.addItems(ProductResponse.newBuilder()
                .setId(p.getId())
                .setName(p.getName())
                .setCategory(p.getCategory().getName())
                .setPrice(p.getPrice() == null ? 0.0 : p.getPrice().doubleValue())
                .setQuantity(p.getQuantity())
                .build());
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
