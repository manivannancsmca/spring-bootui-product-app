package com.bootui.product.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootui.product.app.domain.Product;
import com.bootui.product.app.dto.request.ProductRequest;
import com.bootui.product.app.dto.response.ProductResponse;
import com.bootui.product.app.exception.ProductNotFoundException;
import com.bootui.product.app.exception.ValidationException;
import com.bootui.product.app.mapper.ProductMapper;
import com.bootui.product.app.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new ValidationException("Product with SKU " + request.sku() + " already exists");
        }

        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        log.info("Created product with id: {}", saved.getId());
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (!existing.getSku().equals(request.sku()) && productRepository.existsBySku(request.sku())) {
            throw new ValidationException("Product with SKU " + request.sku() + " already exists");
        }

        productMapper.updateEntityFromRequest(existing, request);
        Product updated = productRepository.save(existing);
        log.info("Updated product with id: {}", updated.getId());
        return productMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Deleted product with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable)
                .map(productMapper::toResponse);
    }
}