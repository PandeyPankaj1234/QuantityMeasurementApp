package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<QuantityRecord, Long> {
}
