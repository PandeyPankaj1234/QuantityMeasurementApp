package com.app.quantitymeasurement.controller;

import java.lang.System.Logger;

import com.app.quantitymeasurement.Service.IQuantityMeasurementService;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);
    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialized");
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.compare(q1, q2);
        displayResult(result);
    }

    public void performConversion(QuantityDTO source, QuantityDTO targetUnit) {
        QuantityMeasurementEntity result = service.convert(source, targetUnit);
        displayResult(result);
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.add(q1, q2);
        displayResult(result);
    }

    public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.subtract(q1, q2);
        displayResult(result);
    }

    public void performDivision(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.divide(q1, q2);
        displayResult(result);
    }

    private void displayResult(QuantityMeasurementEntity entity) {
        if (entity.hasError())
            logger.error("Operation failed: {}", entity.getErrorMessage());
        else
            logger.info("{}", entity);
    }
}