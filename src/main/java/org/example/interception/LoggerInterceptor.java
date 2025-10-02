package org.example.interception;

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
public class LoggerInterceptor  {
  Logger logger = Logger.getLogger(LoggerInterceptor.class);

  @AroundInvoke
  public Object logMethodEntry(InvocationContext invocationContext) throws Throwable {
    logger.info("logMethodEntry: " + invocationContext.getMethod().getName() + " " + Arrays.toString(invocationContext.getParameters()));
    Arrays.toString(invocationContext.getParameters());
    var result = invocationContext.proceed();
    logger.info("Result: " + result);
    return result;
  }
}
