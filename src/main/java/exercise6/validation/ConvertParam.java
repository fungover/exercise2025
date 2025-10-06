package exercise6.validation;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class ConvertParam implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if (aClass.equals(int.class) || aClass.equals(Integer.class)) {
            return (ParamConverter<T>) new IdValidate();
        }
        return null;
    }
}
