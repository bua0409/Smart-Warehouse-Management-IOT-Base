package datn.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import datn.springboot.entity.product;
import datn.springboot.repo.productRepository;

import java.util.List;
import java.util.Optional;

@Service
public class productServiceImpl implements productService {
    private final productRepository productRepository;

    @Autowired
    public productServiceImpl(productRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public product saveProduct(product product) {
        return productRepository.save(product);
    }

    @Override
    public List<product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public product updateProduct(String id, product product) {
        product.setRfid(id);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
