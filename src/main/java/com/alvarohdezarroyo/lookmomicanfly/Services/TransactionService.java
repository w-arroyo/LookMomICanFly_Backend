package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.TransactionRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final SaleService saleService;
    private final TrackingNumberService trackingNumberService;

    public TransactionService(TransactionRepository transactionRepository, OrderService orderService, SaleService saleService, TrackingNumberService trackingNumberService) {
        this.transactionRepository = transactionRepository;
        this.orderService = orderService;
        this.saleService=saleService;
        this.trackingNumberService = trackingNumberService;
    }

    @Transactional
    private Transaction saveTransaction(Order order, Sale sale){
        trackingNumberService.saveSaleTrackingNumber(sale.getId());
        final Transaction transaction=TransactionMapper.createTransaction(order,sale);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createOrderSaleAndTransaction(Ask ask, Bid bid){
        return saveTransaction(createOrder(bid),createSale(ask));
    }

    @Transactional
    private Order createOrder(Bid bid){
        final Order order=TransactionMapper.createOrder(bid);
        return orderService.saveOrder(order);
    }

    @Transactional
    private Sale createSale(Ask ask){
        final Sale sale=TransactionMapper.createSale(ask);
        return saleService.saveSale(sale);
    }

    public String getOrderIdFromTransaction(String saleId){
        return transactionRepository.getOrderIdFromTransaction(saleId).orElseThrow(
                ()-> new EntityNotFoundException("Sale ID does not exist.")
        );
    }
}
