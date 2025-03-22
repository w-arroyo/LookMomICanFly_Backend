package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Calculators.AmountCalculator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.ReferenceGenerator;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static TransactionSummaryDTO saleToTransactionSummaryDTO(Sale sale) throws Exception {
        final TransactionSummaryDTO transaction=new TransactionSummaryDTO();
        transaction.setProduct(ProductMapper.toSummary(sale.getAsk().getProduct()));
        transaction.setReference(sale.getReference());
        transaction.setSize(sale.getAsk().getSize().getValue());
        transaction.setAddress(AddressMapper.toDTO(sale.getAsk().getAddress()));
        transaction.setAmount(AmountCalculator.getAskPayout(sale.getAsk()));
        return transaction;
    }

    public static TransactionSummaryDTO orderToTransactionSummaryDTO(Order order) throws Exception {
        final TransactionSummaryDTO transaction=new TransactionSummaryDTO();
        transaction.setReference(order.getReference());
        transaction.setSize(order.getBid().getSize().getValue());
        transaction.setProduct(ProductMapper.toSummary(order.getBid().getProduct()));
        transaction.setAddress(AddressMapper.toDTO(order.getBid().getAddress()));
        transaction.setAmount(AmountCalculator.getBidTotal(order.getBid()));
        return transaction;
    }

    private static void fillTransactionDTOFields(TransactionDTO transaction, String reference, String tracking, Address address, int amount, String size, Product product, TransactionStatusDTO status) throws Exception {
        transaction.setReference(reference);
        transaction.setAddress(AddressMapper.toDTO(address));
        transaction.setAmount(amount);
        transaction.setSize(size);
        transaction.setTrackingNumber(tracking);
        transaction.setProduct(ProductMapper.toSummary(product));
        transaction.setStatus(status);
    }

    public static SaleDTO toSaleDTO(Sale sale, String tracking) throws Exception {
        final SaleDTO saleDTO=new SaleDTO();
        fillTransactionDTOFields(saleDTO, sale.getReference(),tracking,sale.getAsk().getAddress(),sale.getAsk().getAmount(),sale.getAsk().getSize().getValue(),sale.getAsk().getProduct(), new TransactionStatusDTO(sale.getStatus().name().replace("_"," "),sale.getStatus().getValue()));
        saleDTO.setShippingFee(sale.getAsk().getShippingFee());
        saleDTO.setPercentage(sale.getAsk().getSellingFee().getPercentage());
        return saleDTO;
    }

    public static OrderDTO toOrderDTO(Order order, String tracking) throws Exception {
        final OrderDTO orderDTO=new OrderDTO();
        fillTransactionDTOFields(orderDTO,order.getReference(),tracking,order.getBid().getAddress(),order.getBid().getAmount(),order.getBid().getSize().getValue(),order.getBid().getProduct(),new TransactionStatusDTO(order.getStatus().name().replace("_"," "),order.getStatus().getValue()));
        orderDTO.setOperationalFee(order.getBid().getOperationalFee());
        orderDTO.setShippingOption(ShippingOptionMapper.toDTO(order.getBid().getShippingOption()));
        return orderDTO;
    }

    public static Transaction createTransaction(Order order, Sale sale){
        final Transaction transaction=new Transaction();
        transaction.setSale(sale);
        transaction.setOrder(order);
        return transaction;
    }

    public static Order createOrder(Bid bid){
        final Order order=new Order();
        order.setReference(ReferenceGenerator.generateRandomReference());
        order.setBid(bid);
        order.setStatus(OrderStatus.WAITING);
        return order;
    }

    public static Sale createSale(Ask ask){
        final Sale sale=new Sale();
        sale.setReference(ReferenceGenerator.generateRandomReference());
        sale.setAsk(ask);
        sale.setStatus(SaleStatus.PENDING);
        return sale;
    }

}
