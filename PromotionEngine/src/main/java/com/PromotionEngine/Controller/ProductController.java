package com.PromotionEngine.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PromotionEngine.Entity.Product;
import com.PromotionEngine.Entity.ProductRequest;
import com.PromotionEngine.Exception.ProductNotFoundException;
import com.PromotionEngine.Repository.ProductRepository;

@RestController
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public List<Product> retrieveProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/products/{type}")
	public Product retrieveProductsByType(@PathVariable String type) {
		Optional<Product> product = productRepository.findByType(type);

		if (!product.isPresent())
			throw new ProductNotFoundException("type-" + type);

		return product.get();
	}

	@DeleteMapping("/products/del/{id}")
	public void deleteProduct(@PathVariable long id) {
		productRepository.deleteById(id);
	}

	@PostMapping("/products")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		Product savedProduct = productRepository.save(product);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProduct.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PostMapping("/productstobuy")
	public Integer getTotalPriceAfterPromotionApplied(@RequestBody List<ProductRequest> productRequestList) {
		int counterofA = 0;
		int priceofA = 50;
		int counterofB = 0;
		int priceofB = 30;
		int counterofC = 0;
		int priceofC = 20;
		int counterofD = 0;
		int priceofD = 15;

		for (ProductRequest prodReq : productRequestList) {
			String prodType = prodReq.getProductName();

			switch (prodType) {
			case "A":
				counterofA++;
				break;
			case "a":
				counterofA++;
				break;
			case "B":
				counterofB++;
				break;
			case "b":
				counterofB++;
				break;
			case "C":
				counterofC++;
				break;
			case "c":
				counterofC++;
				break;
			case "D":
				counterofD++;
				break;
			case "d":
				counterofD++;
				break;
			}

		}
		int totalPriceofA = (counterofA / 3) * 130 + (counterofA % 3 * priceofA);
		int totalPriceofB = (counterofB / 2) * 45 + (counterofB % 2 * priceofB);
		int totalPriceofC = (counterofC * priceofC);
		int totalPriceofD = (counterofD * priceofD);
		return totalPriceofA + totalPriceofB + totalPriceofC + totalPriceofD;

	}

}
