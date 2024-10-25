package com.osc.dashboardservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDataRequestDto {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("prodId")
    private String productId;
}
