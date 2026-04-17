package ru.diasoft.ncheranev.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Команда Shell "Отобразить описание задания"
 */
@ShellComponent
@RequiredArgsConstructor
public class TaskCommand {

    @Value("classpath:task.txt")
    private Resource taskResource;

    @ShellMethod("Описание задания")
    public String task() {
        try {
            return taskResource.getContentAsString(Charset.defaultCharset());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
