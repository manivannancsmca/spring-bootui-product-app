package com.bootui.product.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String category,
        String sku,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}