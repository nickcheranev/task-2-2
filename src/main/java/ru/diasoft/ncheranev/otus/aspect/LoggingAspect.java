package ru.diasoft.ncheranev.otus.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.nonNull;

/**
 * Аспект логирования сервисов
 */
@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("execution(* ru.diasoft.ncheranev.otus.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = joinPoint.getSignature().toString();
        var arguments = joinPoint.getArgs();
        log.debug("Вызван метод: {}", signature);
        if (nonNull(arguments) && arguments.length > 0) {
            log.debug("Аргументы: {}", Arrays.stream(arguments).map(Object::toString).toList());
        }
        var result = joinPoint.proceed();
        log.debug("Выполнен метод: {}", signature);
        if (nonNull(result)) {
            log.debug("Результат: {}", result);
        }
        return result;
    }
}
