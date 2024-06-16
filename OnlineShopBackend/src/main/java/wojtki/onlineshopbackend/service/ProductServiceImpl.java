package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.exception.PermissionDeniedException;
import wojtki.onlineshopbackend.model.Product;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.ProductRepository;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void addProduct(Product product, Authentication authentication) {
        Optional<User> seller = userRepository.findByEmail(authentication.getName());

        if(seller.isEmpty()) {
            throw new NotFoundException("Seller not found");
        }

        product.setUser(seller.get());

        productRepository.save(product);
    }

    @Override
    public List<Product> getMyProducts(Authentication authentication) {
        Optional<User> seller = userRepository.findByEmail(authentication.getName());

        if(seller.isEmpty()) {
            throw new NotFoundException("Seller not found");
        }

        return productRepository.findByUserId(seller.get().getId());
    }

    @Override
    public void deleteProduct(Long productId, Authentication authentication) {
        Optional<User> seller = userRepository.findByEmail(authentication.getName());

        if (seller.isEmpty()) {
            throw new NotFoundException("Seller not found");
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        if (!Objects.equals(product.get().getUser().getId(), seller.get().getId())) {
            throw new PermissionDeniedException("You don't have permission to delete this product");
        }

        productRepository.delete(product.get());
    }

    @Override
    public Product updateProduct(Product product, Authentication authentication) {
        Optional<User> seller = userRepository.findByEmail(authentication.getName());

        if (seller.isEmpty()) {
            throw new NotFoundException("Seller not found");
        }

        Optional<Product> productInDatabase = productRepository.findById(product.getId());

        if (productInDatabase.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        if (!Objects.equals(productInDatabase.get().getUser().getId(), seller.get().getId())) {
            throw new PermissionDeniedException("You don't have permission to update this product");
        }

        Product productToUpdate = productInDatabase.get();
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setQuantity(product.getQuantity());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setIsActive(product.getIsActive());

        return productRepository.save(productToUpdate);
    }
}
