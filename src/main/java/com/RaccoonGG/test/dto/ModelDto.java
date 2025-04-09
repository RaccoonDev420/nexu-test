package com.RaccoonGG.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDto implements GenericDto{
    private Long id;
    @NotBlank(message = "Model name is required")
    @Size(min = 2, max = 50, message = "Model name must be between 2 and 50 characters")
    private String name;
    @DecimalMin(value = "100000.01", message = "Average price must be greater than 100,000")
    private BigDecimal averagePrice;
    @NotNull(message = "Brand ID is required")
    private Long brandId;
    private String brandName;
    private String createdAt;
    private String updatedAt;
}
