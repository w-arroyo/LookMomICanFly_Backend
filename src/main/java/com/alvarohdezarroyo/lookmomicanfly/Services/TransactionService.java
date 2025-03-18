package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final SaleService saleService;
    private final AskService askService;
    private final BidService bidService;

    public TransactionService(TransactionRepository transactionRepository, OrderService orderService, SaleService saleService, AskService askService, BidService bidService) {
        this.transactionRepository = transactionRepository;
        this.orderService = orderService;
        this.saleService = saleService;
        this.askService = askService;
        this.bidService = bidService;
    }

}
