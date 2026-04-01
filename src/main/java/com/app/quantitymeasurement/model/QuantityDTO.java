package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotEmpty(message = "Unit cannot be empty")
    private String unit;

    @NotEmpty(message = "Measurement type cannot be empty")
    private String measurementType;

    // Explicit getters and setters (complementing Lombok @Data)
    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return this.measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }
}
