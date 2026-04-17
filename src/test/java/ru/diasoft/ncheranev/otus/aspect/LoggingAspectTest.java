package ru.diasoft.ncheranev.otus.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс LoggingAspect")
@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {
    @InjectMocks
    private LoggingAspect sut;

    @Test
    @DisplayName("Отсутствуют аргументы и результат (аргументы - пустой массив)")
    void logAround_withoutArgumentsAndResult() throws Throwable {
        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);
        when(mockJoinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(mockJoinPoint.getArgs()).thenReturn(new Object[]{});

        var result = sut.logAround(mockJoinPoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Отсутствуют аргументы и результат (null)")
    void logAround_withNullArgumentsAndWithoutResult() throws Throwable {
        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);
        when(mockJoinPoint.getSignature()).thenReturn(mock(Signature.class));

        var result = sut.logAround(mockJoinPoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Присутствуют аргументы и результат")
    void logAround_withArgumentsAndResult() throws Throwable {
        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);
        when(mockJoinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(mockJoinPoint.getArgs()).thenReturn(new Object[]{"argument"});
        when(mockJoinPoint.proceed()).thenReturn("result");

        var result = sut.logAround(mockJoinPoint);

        assertThat(result).isEqualTo("result");
    }
}