package datn.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import datn.springboot.entity.product;
import datn.springboot.repo.productRepository;

import java.util.List;
import java.util.Optional;


public interface productService {
    product saveProduct(product product);
    List<product> getAllProducts();
    Optional<product> getProductById(String id);
    product updateProduct(String id, product product);
    void deleteProduct(String id);
}
