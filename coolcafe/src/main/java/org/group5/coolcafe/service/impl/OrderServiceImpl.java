package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.OrderConverter;
import org.group5.coolcafe.dto.order.OrderCreationRequest;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderUpdateRequest;
import org.group5.coolcafe.entity.HasOrderDetail;
import org.group5.coolcafe.entity.Order;
import org.group5.coolcafe.entity.Product;
import org.group5.coolcafe.enums.OrderStatus;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.OrderDetailRepository;
import org.group5.coolcafe.repository.OrderRepository;
import org.group5.coolcafe.repository.ProductRepository;
import org.group5.coolcafe.repository.ServeTableRepository;
import org.group5.coolcafe.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ServeTableRepository serveTableRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderConverter orderConverter;

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderConverter.toOrderDTO(orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    @Override
    public void createOrder(OrderCreationRequest request) {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setTable(serveTableRepository.getReferenceById(request.getTableId()));
        Order newOrder = orderRepository.save(order);

        List<HasOrderDetail> orderDetails = request.getOrderDetails().stream().map((item) -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            if(product.getEstimatedQuantity() < item.getQuantity()){
                throw new AppException(ErrorCode.UNCATEGORIZED);
            }

            product.setSoldQuantity(product.getSoldQuantity() + item.getQuantity());

            return HasOrderDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .order(newOrder)
                    .build();
        }).toList();

        Double sum = orderDetails.stream()
                .map(item -> item.getQuantity() * item.getProductPrice())
                .reduce(0.0, Double::sum);

        newOrder.setTotalAmount(sum);
        orderRepository.save(newOrder);

        orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public void updateOrder(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        System.out.println(status);
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.stream().map(orderConverter::toOrderDTO).toList();
    }

    public int getTotalPages(int pageSize) {
        long totalItems = orderRepository.count();
        return (int) Math.ceil((double) totalItems / pageSize);
    }

}
