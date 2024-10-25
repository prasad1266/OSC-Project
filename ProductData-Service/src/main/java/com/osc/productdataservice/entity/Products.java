package com.osc.productdataservice.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {
    @Id
    @Column(name = "productid")
    private String productId;
    @Column(name = "categoryid")
    private Character categoryId;
    @Column(name = "productname")
    private String productName;
    @Column(name = "productprice")
    private Double productPrice;
    @Column(name = "productdescription", columnDefinition = "TEXT")
    private String productDescription;
    @Column(name = "imagepath")
    private String imagePath;
}
