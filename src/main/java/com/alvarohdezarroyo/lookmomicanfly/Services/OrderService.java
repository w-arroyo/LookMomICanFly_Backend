package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    private final TrackingNumberService trackingNumberService;

    public OrderService(OrderRepository orderRepository, TrackingNumberService trackingNumberService) {
        this.orderRepository = orderRepository;
        this.trackingNumberService = trackingNumberService;
    }

    public Order getOrderById(String orderId){
        return orderRepository.findById(orderId).orElseThrow(
                ()->new EntityNotFoundException("Order ID does not exist.")
        );
    }

    @Transactional
    public String completeShippedOrders(){
        return orderRepository.completeShippedOrders()+" orders were completed.";
    }

    @Transactional
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    @Transactional
    public void changeOrderStatus(String orderId, OrderStatus status){
        if(orderRepository.changeOrderStatus(orderId,status.name())<1)
            throw new RuntimeException("Server error. Unable to update order status.");
        checkIfOrderNeedsEmail(orderId);
        if(status.equals(OrderStatus.SHIPPED))
            saveOrderTrackingNumber(orderId);
    }

    @Transactional
    private void saveOrderTrackingNumber(String orderId){
        trackingNumberService.saveOrderTrackingNumber(orderId);
    }


    public String getOrderTrackingNumber(String orderId){
        return trackingNumberService.getOrderTrackingNumber(orderId);
    }

    public List<Order> getAllUserOrders(String userId){
        return orderRepository.getAUserOrders(userId);
    }

    private void checkIfOrderNeedsEmail(String orderId){
        final Order order=getOrderById(orderId);
        // USE SWITCH OH MY GOD
        if(order.getStatus().equals(OrderStatus.CANCELLED))
            //send email
            System.out.println("nop");
        else if(order.getStatus().equals(OrderStatus.AUTHENTICATED))
            // send email
            System.out.println("yes");
        else if(order.getStatus().equals(OrderStatus.FAKE_PRODUCT))
            // send email
            System.out.println("no");
        else if(order.getStatus().equals(OrderStatus.SHIPPED)){
            saveOrderTrackingNumber(order.getId());
            // send email
        }
    }

}
