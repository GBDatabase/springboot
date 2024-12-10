package com.mysite.platform.transaction;

import com.mysite.platform.product.ProductService;
import com.mysite.platform.user.UserService;
import com.mysite.platform.user.SiteUser;
import com.mysite.platform.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final ProductService productService;

    // 모든 거래 조회
    @GetMapping
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transaction_list"; // 거래 목록 페이지
    }

    // 거래 생성 폼 페이지
    @GetMapping("/form")
    public String transactionForm(Model model) {
        model.addAttribute("users", userService.getAllUsers()); // 모든 사용자 정보
        model.addAttribute("products", productService.getAllProducts()); // 모든 제품 정보
        return "transaction_form"; // 거래 생성 폼
    }

    // 거래 생성 처리
    @PostMapping
    public String createTransaction(
            @RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        // 사용자와 제품을 조회하여 거래 생성
        SiteUser buyer = userService.getUserById(userId);
        Product product = productService.getProduct(productId);
        transactionService.createTransaction(buyer, product, quantity);
        return "redirect:/transaction"; // 거래 목록 페이지로 리다이렉트
    }

    // 특정 거래 상세 정보 조회
    @GetMapping("/{id}")
    public String getTransaction(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id); // 오류 수정
        model.addAttribute("transaction", transaction);
        return "transaction_detail"; // 거래 상세 페이지
    }

    // 거래 삭제
    @GetMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id); // 오류 수정
        return "redirect:/transaction"; // 거래 목록 페이지로 리다이렉트
    }
}
