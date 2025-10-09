package exercise6.validation;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ext.ParamConverter;

public class IdValidate implements ParamConverter<Integer> {

    @Override
    public Integer fromString(String paramValue) {

        if(paramValue==null || paramValue.isBlank()) {
            throw new BadRequestException("Parameter is null or empty");
        }

        int petId;

        try{
            petId = Integer.parseInt(paramValue);
        }catch(NumberFormatException e){
            throw new BadRequestException("Invalid ID, must be a number");
        }

        if(petId <= 0){
            throw new BadRequestException("Invalid ID, must be a positive number");
        }

        return petId;
    }

    @Override
    public String toString(Integer paramValue) {
        return paramValue.toString();
    }

}
