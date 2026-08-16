package com.bootui.product.app.mapper;

import org.springframework.stereotype.Component;

import com.bootui.product.app.domain.Product;
import com.bootui.product.app.dto.request.ProductRequest;
import com.bootui.product.app.dto.response.ProductResponse;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .category(request.category())
                .sku(request.sku())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getSku(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public void updateEntityFromRequest(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(request.category());
        product.setSku(request.sku());
    }
}
