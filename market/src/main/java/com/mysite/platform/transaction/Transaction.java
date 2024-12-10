package com.mysite.platform.transaction;

import com.mysite.platform.product.Product;
import com.mysite.platform.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SiteUser buyer; // 구매자

    @ManyToOne
    private Product product; // 거래된 상품

    private Integer quantity; // 거래된 수량

    private Double totalPrice; // 총 금액

    private LocalDateTime transactionDate; // 거래 날짜
}
