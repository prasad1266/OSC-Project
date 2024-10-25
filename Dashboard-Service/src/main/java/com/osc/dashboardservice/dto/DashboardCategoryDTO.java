package com.osc.dashboardservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCategoryDTO {
    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("category Name")
    private String categoryName;

    @JsonProperty("count")
    private Long count;
}
