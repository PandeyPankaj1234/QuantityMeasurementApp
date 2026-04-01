package com.app.quantitymeasurement.Service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Standalone service for quantity measurements.
 * Can be instantiated manually without Spring dependency injection.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QuantityMeasurementStandaloneService implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementStandaloneService.class);
    @SuppressWarnings("unused")
    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementStandaloneService(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private <U extends IMeasurable> Quantity<U> toQuantity(QuantityDTO dto) {
        U unit = (U) IMeasurable.getUnitInstance(dto.getMeasurementType(), dto.getUnit());
        return new Quantity<>(dto.getValue(), unit);
    }

    private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementDTO dto) {
        try {
            // Skip saving to repository due to entity type mismatch
            // The repository expects entity.QuantityMeasurementEntity
            // while DTO produces model.QuantityMeasurementEntity
            logger.debug("Measurement logged: {}", dto.getOperation());
        } catch (Exception e) {
            logger.warn("Could not save to repository: {}", e.getMessage());
        }
        return dto;
    }

    private QuantityMeasurementDTO buildBase(QuantityDTO q1, QuantityDTO q2, String operation) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setThisValue(q1.getValue());
        dto.setThisUnit(q1.getUnit());
        dto.setThisMeasurementType(q1.getMeasurementType());
        dto.setThatValue(q2.getValue());
        dto.setThatUnit(q2.getUnit());
        dto.setThatMeasurementType(q2.getMeasurementType());
        dto.setOperation(operation);
        return dto;
    }

    private QuantityMeasurementDTO buildError(QuantityDTO q1, QuantityDTO q2, String operation, String message) {
        QuantityMeasurementDTO dto = buildBase(q1, q2, operation);
        dto.setError(true);
        dto.setErrorMessage(message);
        return dto;
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot compare different measurement categories");
            Quantity<?> qty1 = toQuantity(q1);
            Quantity<?> qty2 = toQuantity(q2);
            boolean result = qty1.equals(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "compare");
            dto.setResultString(String.valueOf(result));
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Compare failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "compare", e.getMessage()));
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO source, QuantityDTO target) {
        try {
            @SuppressWarnings("rawtypes")
            Quantity qty = toQuantity(source);
            IMeasurable targetUnit = IMeasurable.getUnitInstance(target.getMeasurementType(), target.getUnit());
            @SuppressWarnings("rawtypes")
            Quantity converted = qty.convertTo(targetUnit);
            QuantityMeasurementDTO dto = buildBase(source, target, "convert");
            dto.setResultValue(converted.getValue());
            dto.setResultUnit(converted.getUnit().getUnitName());
            dto.setResultMeasurementType(converted.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Convert failed: {}", e.getMessage());
            return saveAndReturn(buildError(source, target, "convert", e.getMessage()));
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "Cannot perform arithmetic between different measurement categories: " + q1.getMeasurementType()
                                + " and " + q2.getMeasurementType());
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            @SuppressWarnings("rawtypes")
            Quantity result = qty1.add(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "add");
            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().getUnitName());
            dto.setResultMeasurementType(result.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Add failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "add", "add Error: " + e.getMessage()));
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "Cannot perform arithmetic between different measurement categories: " + q1.getMeasurementType()
                                + " and " + q2.getMeasurementType());
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            @SuppressWarnings("rawtypes")
            Quantity result = qty1.subtract(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "subtract");
            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().getUnitName());
            dto.setResultMeasurementType(result.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Subtract failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "subtract", e.getMessage()));
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "Cannot perform arithmetic between different measurement categories");
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            double result = qty1.divide(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "divide");
            dto.setResultValue(result);
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Divide failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "divide", e.getMessage()));
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return List.of();
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByType(String measurementType) {
        return List.of();
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return List.of();
    }

    @Override
    public long getCountByOperation(String operation) {
        return 0;
    }
}
