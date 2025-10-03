package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PetQueryParams {

    @QueryParam("offset")
    @DefaultValue("0")
    @Min(value = 0, message = "Offset must be greater than or equal to 0")
    private int offset;

    @QueryParam("limit")
    @DefaultValue("10")
    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 100, message = "Limit cannot be greater than 100")
    private int limit;

    @QueryParam("species")
    private String species;

    @QueryParam("sortBy")
    @DefaultValue("id")
    private String sortBy;

    @QueryParam("order")
    @DefaultValue("asc")
    private String order;

    
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
