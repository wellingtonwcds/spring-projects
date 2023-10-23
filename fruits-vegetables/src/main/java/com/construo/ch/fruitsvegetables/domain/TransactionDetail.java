package com.construo.ch.fruitsvegetables.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_detail")
@Data
@ToString(of = {"id", "transaction", "product", "quantity", "subTotal"})
@EqualsAndHashCode(of = {"id", "transaction", "product", "quantity"}, callSuper = false)
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_detail_seq")
    @SequenceGenerator(name = "transaction_detail_seq", sequenceName = "transaction_detail_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "subTotal")
    private BigDecimal subTotal;
}
