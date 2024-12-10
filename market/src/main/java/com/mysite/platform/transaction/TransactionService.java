package com.mysite.platform.transaction;

import com.mysite.platform.product.Product;
import com.mysite.platform.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    // 모든 거래 조회
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 특정 거래 조회
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }

    // 새로운 거래 생성
    public Transaction createTransaction(SiteUser buyer, Product product, Integer quantity) {
        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setTotalPrice(product.getPrice() * quantity);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    // 거래 삭제
    public void deleteTransaction(Long id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }
}
