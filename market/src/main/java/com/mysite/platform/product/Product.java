package com.mysite.platform.product;

import com.mysite.platform.transaction.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // 품목코드

    private String name; // 품목명

    private Integer quantity; // 수량

    private Double price; // 단가

    private String vendor; // 판매처명

    private String category; // 카테고리

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions; // 연관된 거래 내역
}
