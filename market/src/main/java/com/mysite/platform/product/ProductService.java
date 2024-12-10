package com.mysite.platform.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 제품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 제품 ID로 조회
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    // 제품 저장
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 제품 삭제
    public void deleteProduct(Long productId) {
        Product product = getProduct(productId);

        // 연관된 데이터 확인 후 삭제
        if (!product.getTransactions().isEmpty()) {
            throw new RuntimeException("Cannot delete product with existing transactions.");
        }

        productRepository.deleteById(productId);
    }

    // 제품 재고 업데이트
    public void updateProductStock(Product product, int quantitySold) {
        if (product.getQuantity() < quantitySold) {
            throw new RuntimeException("Not enough stock available.");
        }
        product.setQuantity(product.getQuantity() - quantitySold);
        productRepository.save(product);
    }
}
