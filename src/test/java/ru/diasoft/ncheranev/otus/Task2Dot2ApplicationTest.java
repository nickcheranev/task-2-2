package ru.diasoft.ncheranev.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Класс Task8Application")
class Task2Dot2ApplicationTest {

    @Test
    @DisplayName("main() должен выполниться без ошибок")
    void shouldRunWithoutException() {
        assertThatCode(() -> Task2Dot2Application.main(new String[] {"arg"}))
                .doesNotThrowAnyException();
    }
}