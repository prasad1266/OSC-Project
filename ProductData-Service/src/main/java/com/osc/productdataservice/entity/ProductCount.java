package com.osc.productdataservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.avro.specific.SpecificRecord;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_view_count")
public class ProductCount {
    @Id
    @Column(name = "productid")
    private String productId;
    @Column(name = "categoryid")
    private Character categoryId;
    @Column(name = "viewcount")
    private int viewCount;
}