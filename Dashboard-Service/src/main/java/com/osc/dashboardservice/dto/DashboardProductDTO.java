package com.osc.dashboardservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProductDTO {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("prodName")
    private String prodName;

    @JsonProperty("prodMarketPrice")
    private String prodMarketPrice;

    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("productDescription")
    private String productDescription;
}
