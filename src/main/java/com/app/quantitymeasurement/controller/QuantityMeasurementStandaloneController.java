package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.Service.IQuantityMeasurementService;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standalone controller for console-based quantity measurement operations.
 * Does not require Spring annotations for manual instantiation.
 * Converts between entity.QuantityDTO and model.QuantityDTO for compatibility.
 */
public class QuantityMeasurementStandaloneController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementStandaloneController.class);
    private final IQuantityMeasurementService service;

    public QuantityMeasurementStandaloneController(IQuantityMeasurementService service) {
        this.service = service;
    }

    private com.app.quantitymeasurement.model.QuantityDTO convertToModel(QuantityDTO entityDto) {
        com.app.quantitymeasurement.model.QuantityDTO modelDto = new com.app.quantitymeasurement.model.QuantityDTO();
        modelDto.setValue(entityDto.getValue());
        modelDto.setUnit(entityDto.getUnit().getUnitName());
        modelDto.setMeasurementType(entityDto.getUnit().getMeasurementType());
        return modelDto;
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        try {
            logger.info("Comparing {} {} with {} {}",
                    q1.getValue(), q1.getUnit().getUnitName(),
                    q2.getValue(), q2.getUnit().getUnitName());
            QuantityMeasurementDTO result = service.compare(convertToModel(q1), convertToModel(q2));
            if (result.isError()) {
                logger.error("Comparison error: {}", result.getErrorMessage());
            } else {
                logger.info("Comparison result: {} {} = {} {} ? {}",
                        q1.getValue(), q1.getUnit().getUnitName(),
                        q2.getValue(), q2.getUnit().getUnitName(),
                        result.getResultString());
            }
        } catch (Exception e) {
            logger.error("Comparison failed", e);
        }
    }

    public void performConversion(QuantityDTO source, QuantityDTO target) {
        try {
            logger.info("Converting {} {} to {}",
                    source.getValue(), source.getUnit().getUnitName(), target.getUnit().getUnitName());
            QuantityMeasurementDTO result = service.convert(convertToModel(source), convertToModel(target));
            if (result.isError()) {
                logger.error("Conversion error: {}", result.getErrorMessage());
            } else {
                logger.info("Conversion result: {} {} = {} {}",
                        source.getValue(), source.getUnit().getUnitName(),
                        result.getResultValue(), result.getResultUnit());
            }
        } catch (Exception e) {
            logger.error("Conversion failed", e);
        }
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        try {
            logger.info("Adding {} {} + {} {}",
                    q1.getValue(), q1.getUnit().getUnitName(),
                    q2.getValue(), q2.getUnit().getUnitName());
            QuantityMeasurementDTO result = service.add(convertToModel(q1), convertToModel(q2));
            if (result.isError()) {
                logger.error("Addition error: {}", result.getErrorMessage());
            } else {
                logger.info("Addition result: {} {} + {} {} = {} {}",
                        q1.getValue(), q1.getUnit().getUnitName(),
                        q2.getValue(), q2.getUnit().getUnitName(),
                        result.getResultValue(), result.getResultUnit());
            }
        } catch (Exception e) {
            logger.error("Addition failed", e);
        }
    }

    public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        try {
            logger.info("Subtracting {} {} - {} {}",
                    q1.getValue(), q1.getUnit().getUnitName(),
                    q2.getValue(), q2.getUnit().getUnitName());
            QuantityMeasurementDTO result = service.subtract(convertToModel(q1), convertToModel(q2));
            if (result.isError()) {
                logger.error("Subtraction error: {}", result.getErrorMessage());
            } else {
                logger.info("Subtraction result: {} {} - {} {} = {} {}",
                        q1.getValue(), q1.getUnit().getUnitName(),
                        q2.getValue(), q2.getUnit().getUnitName(),
                        result.getResultValue(), result.getResultUnit());
            }
        } catch (Exception e) {
            logger.error("Subtraction failed", e);
        }
    }

    public void performDivision(QuantityDTO q1, QuantityDTO q2) {
        try {
            logger.info("Dividing {} {} / {} {}",
                    q1.getValue(), q1.getUnit().getUnitName(),
                    q2.getValue(), q2.getUnit().getUnitName());
            QuantityMeasurementDTO result = service.divide(convertToModel(q1), convertToModel(q2));
            if (result.isError()) {
                logger.error("Division error: {}", result.getErrorMessage());
            } else {
                logger.info("Division result: {} {} / {} {} = {}",
                        q1.getValue(), q1.getUnit().getUnitName(),
                        q2.getValue(), q2.getUnit().getUnitName(),
                        result.getResultValue());
            }
        } catch (Exception e) {
            logger.error("Division failed", e);
        }
    }
}
