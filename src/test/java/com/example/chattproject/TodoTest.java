package com.example.chattproject;

import com.example.chattproject.dao.TodoDAO;
import com.example.chattproject.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoTest {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception{

        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testInsert() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO);

    }

    @Test
    public void testList() throws Exception {
        List<TodoVO> todoVOList = todoDAO.selectAll();

        todoVOList.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception{

        Long tno = 1L;

        TodoVO vo = todoDAO.selectOne(tno);

        System.out.println(vo);
    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title Change")
                .dueDate(LocalDate.of(2021,12,31))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);
    }
}

