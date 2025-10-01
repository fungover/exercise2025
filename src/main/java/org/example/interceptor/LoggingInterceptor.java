package org.example.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.example.annotations.Log;
import org.jboss.logging.Logger;

import java.util.Arrays;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class LoggingInterceptor {
    Logger logger = Logger.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        logger.info("Entering method: " + ctx.getMethod().getName() + ", " +
                Arrays.toString(ctx.getParameters()));
        var result = ctx.proceed();
        logger.info("Result from running: " +  result);
        return result;
    }
}