package com.example.chattproject.service;

import com.example.chattproject.dao.TodoDAO;
import com.example.chattproject.domain.TodoVO;
import com.example.chattproject.dto.TodoDTO;
import com.example.chattproject.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService() {
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public void register(TodoDTO todoDTO) throws Exception{

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        System.out.println("todoVO: " + todoVO);
        log.info(todoVO);

        dao.insert(todoVO); //int를 반환하므로 이를 이용해서 예외처리도 간으
    }
}
