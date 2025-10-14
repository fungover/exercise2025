package org.example.resource;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class AmountParams {
    @DefaultValue("10")
    @QueryParam("amount")
    @Min(0) @Max(100)
    public int amount;
}
