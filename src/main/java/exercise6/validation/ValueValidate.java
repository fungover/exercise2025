package exercise6.validation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ValueValidate {
    @NotNull(message = "Value must be entered")
    @Min(value = 1, message = "Value can not be less than 1")
    @Max(value = 100, message = "Value can not exceed 100")
    private int requestValue;

    public int getRequestValue() {
        return requestValue;
    }
    public void setRequestValue(int requestValue) {
        this.requestValue = requestValue;
    }

}
