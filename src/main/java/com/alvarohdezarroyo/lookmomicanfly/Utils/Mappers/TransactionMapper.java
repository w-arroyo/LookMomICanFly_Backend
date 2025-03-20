package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.TransactionDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Calculators.AmountCalculator;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static TransactionDTO saleToDTO(Sale sale) throws Exception {
        final TransactionDTO transaction=new TransactionDTO();
        transaction.setProduct(ProductMapper.toSummary(sale.getAsk().getProduct()));
        transaction.setSize(sale.getAsk().getSize().getValue());
        transaction.setAddress(AddressMapper.toDTO(sale.getAsk().getAddress()));
        transaction.setAmount(AmountCalculator.getAskPayout(sale.getAsk()));
        return transaction;
    }

    public static TransactionDTO orderToDTO(Order order) throws Exception {
        final TransactionDTO transaction=new TransactionDTO();
        transaction.setSize(order.getBid().getSize().getValue());
        transaction.setProduct(ProductMapper.toSummary(order.getBid().getProduct()));
        transaction.setAddress(AddressMapper.toDTO(order.getBid().getAddress()));
        transaction.setAmount(AmountCalculator.getBidTotal(order.getBid()));
        return transaction;
    }

    public static Transaction createTransaction(Order order, Sale sale){
        final Transaction transaction=new Transaction();
        transaction.setSale(sale);
        transaction.setOrder(order);
        System.out.println(transaction.getSale().getId()+"/"+transaction.getOrder().getId()+"/"+transaction.getDate());
        return transaction;
    }

    public static Order createOrder(Bid bid){
        final Order order=new Order();
        order.setBid(bid);
        order.setStatus(OrderStatus.WAITING);
        return order;
    }

    public static Sale createSale(Ask ask){
        final Sale sale=new Sale();
        sale.setAsk(ask);
        sale.setStatus(SaleStatus.PENDING);
        return sale;
    }

}
