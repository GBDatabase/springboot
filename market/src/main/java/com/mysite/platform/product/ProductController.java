package com.mysite.platform.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product_list";
    }

    @GetMapping("/add")
    public String productForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product_form";
    }

    @PostMapping
    public String createProduct(@ModelAttribute ProductForm productForm) {
        Product product = new Product();
        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
        product.setQuantity(productForm.getQuantity());
        product.setPrice(productForm.getPrice());
        product.setVendor(productForm.getVendor());
        product.setCategory(productForm.getCategory());
        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product_detail";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        ProductForm productForm = new ProductForm();
        productForm.setCode(product.getCode());
        productForm.setName(product.getName());
        productForm.setQuantity(product.getQuantity());
        productForm.setPrice(product.getPrice());
        productForm.setVendor(product.getVendor());
        productForm.setCategory(product.getCategory());
        model.addAttribute("productForm", productForm);
        model.addAttribute("productId", id);
        return "product_form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute ProductForm productForm) {
        Product product = productService.getProduct(id);
        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
        product.setQuantity(productForm.getQuantity());
        product.setPrice(productForm.getPrice());
        product.setVendor(productForm.getVendor());
        product.setCategory(productForm.getCategory());
        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }
}
