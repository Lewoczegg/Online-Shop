package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void addProduct(Product product, Authentication authentication);
    List<Product> getMyProducts(Authentication authentication);
    void deleteProduct(Long productId, Authentication authentication);
    Product updateProduct(Product product, Authentication authentication);
}
