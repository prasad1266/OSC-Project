package com.osc.productdataservice.repository;

import com.osc.productdataservice.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, String> {

}
