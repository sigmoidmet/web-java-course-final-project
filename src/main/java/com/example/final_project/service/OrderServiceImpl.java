package com.example.final_project.service;

import com.example.final_project.exception.NotAvailableProductException;
import com.example.final_project.exception.NotPendingOrderCancellingException;
import com.example.final_project.exception.OrderCancellingByAnotherClientException;
import com.example.final_project.exception.OrderWithEmptyCartException;
import com.example.final_project.model.Client;
import com.example.final_project.model.Product;
import com.example.final_project.model.ProductOrder;
import com.example.final_project.pojo.Cart;
import com.example.final_project.pojo.CartItem;
import com.example.final_project.pojo.OrderDto;
import com.example.final_project.pojo.OrderStatus;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.utils.OrderMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderMapper orderMapper;

    private final ClientService clientService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ProductOrder orderItemsForClient(Cart cart, String email) {
        Client client = clientService.getByEmail(email);
        return orderRepository.save(
                ProductOrder.builder()
                        .date(LocalDate.now())
                        .client(client)
                        .status(OrderStatus.Pending)
                        .total(calculateTotal(cart))
                        .build()
        );
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(this::calculateSumAndUpdateProductIfAvailable)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new OrderWithEmptyCartException("Cart can't be empty"));
    }

    private BigDecimal calculateSumAndUpdateProductIfAvailable(CartItem item) {
        BigDecimal productPrice = updateProductIfAvailableAndGetPrice(item);
        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
        return productPrice.multiply(quantity);
    }

    private BigDecimal updateProductIfAvailableAndGetPrice(CartItem item) {
        Product product = productService.getById(item.getId());

        if (product.getAvailable() < item.getQuantity()) {
            throw new NotAvailableProductException(item.getQuantity(), item.getId());
        }

        product.setAvailable(product.getAvailable() - item.getQuantity());

        return productService.save(product).getPrice();
    }

    @Override
    public void cancelOrderForClient(ProductOrder productOrder, String email) {
        validateOrder(productOrder, email);

        productOrder.setStatus(OrderStatus.Canceled);
        orderRepository.save(productOrder);
    }

    private void validateOrder(ProductOrder productOrder, String email) {
        if (!productOrder.getClient().getEmail().equals(email)) {
            throw new OrderCancellingByAnotherClientException("You can't cancel another client's order");
        } else if (!productOrder.getStatus().equals(OrderStatus.Pending)) {
            throw new NotPendingOrderCancellingException("You can't cancel order in other status than pending");
        }
    }

    @Override
    public List<OrderDto> getAllForClient(String email) {
        return orderRepository.findAllByClientEmail(email).stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }
}
