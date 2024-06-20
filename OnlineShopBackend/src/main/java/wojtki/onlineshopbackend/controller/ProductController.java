package wojtki.onlineshopbackend.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.Product;
import wojtki.onlineshopbackend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/get")
    public ResponseEntity<Product> getProductById(@RequestParam Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody Product product, Authentication authentication) {
        productService.addProduct(product, authentication);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), "Successfully added new product");
        logger.info("Product added successfully: {} by user: {}", product.getName(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/my-products")
    public ResponseEntity<List<Product>> getMyProducts(Authentication authentication) {
        List<Product> myProducts = productService.getMyProducts(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(myProducts);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long productId, Authentication authentication) {
        productService.deleteProduct(productId, authentication);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NO_CONTENT.value(), "Product deleted");
        logger.info("Product with ID: {} deleted by user: {}", productId, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, Authentication authentication) {
        Product updatedProduct = productService.updateProduct(product, authentication);
        logger.info("Product updated successfully: {} by user: {}", updatedProduct.getName(), authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
}
