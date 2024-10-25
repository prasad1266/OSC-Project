package com.osc.dashboardservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCategoryListDTO {
    @JsonProperty("TYPE")
    private String type;

    @JsonProperty("Categories")
    private List<DashboardCategoryDTO> Categories;
}
