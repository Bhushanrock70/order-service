package com.smartinventory.order_service.service;

import com.smartinventory.order_service.model.Order;
import com.smartinventory.order_service.model.ProductResponse;
import com.smartinventory.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private  RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Order placeOrder(String productCode, Integer quantity) {
        if (productCode == null || quantity == null) {
            throw new IllegalArgumentException("productCode and quantity must not be null");
        }

        // Call Product Service using productCode
        ProductResponse product = restTemplate.getForObject(productServiceUrl + "/" + productCode, ProductResponse.class);

        if (product == null) {
            throw new RuntimeException("Product not found with code: " + productCode);
        }

        Order order = new Order();
        order.setProductCode(productCode);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


}
