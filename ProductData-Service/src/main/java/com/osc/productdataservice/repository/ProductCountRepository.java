package com.osc.productdataservice.repository;

import com.osc.productdataservice.entity.ProductCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCount, String> {


}
