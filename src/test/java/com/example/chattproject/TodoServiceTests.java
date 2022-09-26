package com.example.chattproject;

import com.example.chattproject.dto.TodoDTO;
import com.example.chattproject.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Log4j2
public class TodoServiceTests {

    private TodoService todoService;

    @BeforeEach
    public void ready(){
        todoService = TodoService.INSTANCE;
    }

    @Test
    public void testRegister() throws Exception{

        TodoDTO todoDTO = TodoDTO.builder()
                .title("JDBC Test title")
                .dueDate(LocalDate.now())
                .build();

        log.info("----------------------------------------------------");
        log.info(todoDTO);

        todoService.register(todoDTO);
    }
}
