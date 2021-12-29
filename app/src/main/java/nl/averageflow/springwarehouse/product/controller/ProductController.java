package nl.averageflow.springwarehouse.product.controller;

import nl.averageflow.springwarehouse.product.model.Product;
import nl.averageflow.springwarehouse.transaction.model.Transaction;
import nl.averageflow.springwarehouse.product.dto.AddProductRequest;
import nl.averageflow.springwarehouse.product.dto.EditProductRequest;
import nl.averageflow.springwarehouse.product.dto.SellProductsRequest;
import nl.averageflow.springwarehouse.product.service.ProductService;
import nl.averageflow.springwarehouse.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public final class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/products")
    public Page<Product> getProducts(final Pageable pageable) {
        return this.productService.getProducts(pageable);
    }

    @GetMapping("/api/products/{uid}")
    public Optional<Product> getProduct(@PathVariable final UUID uid) {
        return this.productService.getProductByUid(uid);
    }

    @PostMapping("/api/products")
    public void addProducts(@RequestBody final AddProductRequest request) {
        this.productService.addProducts(request.getProducts());
    }

    @DeleteMapping("/api/products/{uid}")
    public void deleteProduct(@PathVariable final UUID uid) {
        this.productService.deleteProductByUid(uid);
    }

    @PatchMapping("/api/products/{uid}")
    public Product editProduct(@PathVariable final UUID uid, @RequestBody final EditProductRequest request) {
        return this.productService.editProduct(uid, request);
    }

    @PatchMapping("/api/products/sell")
    public Transaction sellProducts(@RequestBody final SellProductsRequest request) {
        this.productService.sellProducts(request);
        return this.transactionService.createTransaction(request);
    }
}
