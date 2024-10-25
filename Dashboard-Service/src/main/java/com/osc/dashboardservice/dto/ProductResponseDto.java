package com.osc.dashboardservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    @JsonProperty("TYPE")
    private String type;

    @JsonProperty("Similar Products")
    private List<SimilarProductDto> similarProducts;
}
