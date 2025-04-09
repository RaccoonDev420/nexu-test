package com.RaccoonGG.test.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelPriceUpdateDto {
    @DecimalMin(value = "100000.01", message = "Average price must be greater than 100,000")
    private BigDecimal averagePrice;
}
