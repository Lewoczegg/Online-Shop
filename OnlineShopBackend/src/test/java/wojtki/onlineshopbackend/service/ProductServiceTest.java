package wojtki.onlineshopbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.exception.PermissionDeniedException;
import wojtki.onlineshopbackend.model.Product;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.ProductRepository;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_returnsProductList() {
        List<Product> expectedProducts = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void addProduct_sellerNotFound_throwsNotFoundException() {
        Product product = new Product();
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.addProduct(product, authentication));
    }

    @Test
    void addProduct_success() {
        Product product = new Product();
        User seller = new User();
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));

        productService.addProduct(product, authentication);

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(productRepository, times(1)).save(product);
        assertEquals(seller, product.getUser());
    }

    @Test
    void getMyProducts_sellerNotFound_throwsNotFoundException() {
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getMyProducts(authentication));
    }

    @Test
    void getMyProducts_returnsProductList() {
        User seller = new User();
        seller.setId(1L);
        List<Product> expectedProducts = List.of(new Product(), new Product());

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findByUserId(seller.getId())).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getMyProducts(authentication);

        assertEquals(expectedProducts, actualProducts);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(productRepository, times(1)).findByUserId(seller.getId());
    }

    @Test
    void deleteProduct_sellerNotFound_throwsNotFoundException() {
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(1L, authentication));
    }

    @Test
    void deleteProduct_productNotFound_throwsNotFoundException() {
        User seller = new User();
        seller.setId(1L);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(1L, authentication));
    }

    @Test
    void deleteProduct_noPermission_throwsPermissionDeniedException() {
        User seller = new User();
        seller.setId(1L);
        User anotherUser = new User();
        anotherUser.setId(2L);
        Product product = new Product();
        product.setUser(anotherUser);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(PermissionDeniedException.class, () -> productService.deleteProduct(1L, authentication));
    }

    @Test
    void deleteProduct_success() {
        User seller = new User();
        seller.setId(1L);
        Product product = new Product();
        product.setUser(seller);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L, authentication);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void updateProduct_sellerNotFound_throwsNotFoundException() {
        Product product = new Product();
        product.setId(1L);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.updateProduct(product, authentication));
    }

    @Test
    void updateProduct_productNotFound_throwsNotFoundException() {
        User seller = new User();
        seller.setId(1L);
        Product product = new Product();
        product.setId(1L);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.updateProduct(product, authentication));
    }

    @Test
    void updateProduct_noPermission_throwsPermissionDeniedException() {
        User seller = new User();
        seller.setId(1L);
        User anotherUser = new User();
        anotherUser.setId(2L);
        Product product = new Product();
        product.setId(1L);
        Product productInDatabase = new Product();
        productInDatabase.setId(1L);
        productInDatabase.setUser(anotherUser);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productInDatabase));

        assertThrows(PermissionDeniedException.class, () -> productService.updateProduct(product, authentication));
    }

    @Test
    void updateProduct_success() {
        User seller = new User();
        seller.setId(1L);
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Name");
        product.setDescription("Updated Description");
        product.setQuantity(10);
        product.setPrice(100.0);
        product.setIsActive(true);

        Product productInDatabase = new Product();
        productInDatabase.setId(1L);
        productInDatabase.setUser(seller);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(seller));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productInDatabase));
        when(productRepository.save(any(Product.class))).thenReturn(productInDatabase);

        Product updatedProduct = productService.updateProduct(product, authentication);

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(productInDatabase);

        assertEquals("Updated Name", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(10, updatedProduct.getQuantity());
        assertEquals(100.0, updatedProduct.getPrice());
        assertTrue(updatedProduct.getIsActive());
    }
}