package com.alvarohdezarroyo.lookmomicanfly.Services;

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
    public Transaction saveTransaction(Order order, Sale sale){
        trackingNumberService.saveSaleTrackingNumber(sale.getId());
        return transactionRepository.save(TransactionMapper.createTransaction(order,sale));
    }

    @Transactional
    public Order createOrder(Bid bid){
        return orderService.saveOrder(TransactionMapper.createOrder(bid));
    }

    @Transactional
    public Sale createSale(Ask ask){
        return saleService.saveSale(TransactionMapper.createSale(ask));
    }
}
