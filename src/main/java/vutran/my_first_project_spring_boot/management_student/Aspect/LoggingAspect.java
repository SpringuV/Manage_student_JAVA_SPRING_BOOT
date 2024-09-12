package vutran.my_first_project_spring_boot.management_student.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vutran.my_first_project_spring_boot.management_student.Util.StringUtils;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    // PATTERN: execution(modifiers-pattern?return-type-pattern/declaring-type-pattern?method-name-pattern(param-pattern)throws-pattern?)

    // ".": use direct the classes or methods in Service
    // "..": represent for all the child class or child package
    @Around("execution(* vutran.my_first_project_spring_boot.management_student.Service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed(); // proceed with the actual method call

        long executionTime = System.currentTimeMillis() - start;

        // get full signature of method (include class name and method)
        String fullMethodSignature = joinPoint.getSignature().toString();

        // substring package, get Package contain method and method
        String shortenedSignature = StringUtils.shortenSignature(fullMethodSignature);
        logger.info(shortenedSignature+ " executed in "+ executionTime+ "ms");
        return proceed;
    }



}
