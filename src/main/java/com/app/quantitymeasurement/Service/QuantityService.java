package com.app.quantitymeasurement.Service;

import com.app.quantitymeasurement.dto.InputDTO;

public interface QuantityService {
    boolean compare(InputDTO a, InputDTO b);
    double add(InputDTO a, InputDTO b);
    double convert(InputDTO a, String target);
}
