package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.TransactionRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final SaleService saleService;
    private final TrackingNumberService trackingNumberService;
    private final EmailSenderService emailSenderService;

    public TransactionService(TransactionRepository transactionRepository, OrderService orderService, SaleService saleService, TrackingNumberService trackingNumberService, EmailSenderService emailSenderService) {
        this.transactionRepository = transactionRepository;
        this.orderService = orderService;
        this.saleService=saleService;
        this.trackingNumberService = trackingNumberService;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    private Transaction saveTransaction(Order order, Sale sale){
        final TrackingNumber trackingNumber=trackingNumberService.saveSaleTrackingNumber(sale.getId());
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateOrderParams(order));
        emailSenderService.sendEmailWithAttachment(EmailParamsGenerator.generateSaleParams(sale,trackingNumber),sale,trackingNumber);
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

    public LocalDateTime getTransactionDate(String id){
        return transactionRepository.getTransactionDate(id);
    }

}
