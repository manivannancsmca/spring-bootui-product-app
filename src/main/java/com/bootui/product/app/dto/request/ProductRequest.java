package com.bootui.product.app.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity,

        @Size(max = 100, message = "Category must not exceed 100 characters")
        String category,

        @NotBlank(message = "SKU is required")
        @Pattern(regexp = "^[A-Z0-9-]+$", message = "SKU must contain only uppercase letters, numbers, and hyphens")
        @Size(max = 100, message = "SKU must not exceed 100 characters")
        String sku
) {}