package com.bootui.product.app.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        boolean success,
        String message,
        Map<String, String> errors,
        LocalDateTime timestamp
) {}
