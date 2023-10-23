package com.construo.ch.fruitsvegetables.domain;

import com.construo.ch.fruitsvegetables.util.CategoryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@ToString(of = {"id", "name", "category"})
@EqualsAndHashCode(of = {"id", "name", "category", "merchant"}, callSuper = false)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private CategoryEnum category;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;


}
