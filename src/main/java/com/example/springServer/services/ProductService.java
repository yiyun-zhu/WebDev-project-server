package com.example.springServer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.repositories.SellerRepository;
import com.example.springServer.repositories.ProductRepository;
import com.example.springServer.models.Product;
import com.example.springServer.models.Seller;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductService {
	@Autowired 
	ProductRepository productRepository;
	@Autowired
	SellerRepository sellerRepository;
	
	@GetMapping("/api/movie/{movieId}/products")
	public List<Product> findProductsByMovieId(
			@PathVariable("movieId") String movieId) {
		return (List<Product>)productRepository.findProductsForMovie(movieId);
	}
	
	@GetMapping("/api/seller/{sid}/product")
	public List<Product> findProductsBySellerId(@PathVariable("sid") int id) {
		Optional<Seller> data = sellerRepository.findById(id);
		if (data.isPresent()) {
			Seller seller = data.get();
			List<Product>  products = seller.getProducts();
			return products;
		}
		return null;
	}
	
	@GetMapping("/api/product/{pid}")
	public Product findProductByProductId(@PathVariable("pid") int id) {
		Optional<Product> data = productRepository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/seller/{sid}/product")
	public Product createProduct(@PathVariable("sid") int id, @RequestBody Product product) {
		Optional<Seller> data = sellerRepository.findById(id);
		if (data.isPresent()) {
			Seller seller = data.get();
			product.setSeller(seller);
			return productRepository.save(product);
		}
		return null;
	}
	
	@PutMapping("/api/product/{pid}")
	public Product updateProduct(@PathVariable("pid") int id, @RequestBody Product product) {
		Optional<Product> data = productRepository.findById(id);
		if (data.isPresent()) {
			Product oldProduct = data.get();
			oldProduct.setProduct(product);
			productRepository.save(oldProduct);
			return oldProduct;
		}
		return null;
	}
	
	@DeleteMapping("/api/product/{pid}")
	public void deleteProduct(@PathVariable("pid") int id) {
		productRepository.deleteById(id);
	}
}
