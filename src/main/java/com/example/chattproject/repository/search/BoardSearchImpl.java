package com.example.chattproject.repository.search;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.domain.entity.QBoard;
import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {


    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;        //Q도메인 객체

        JPQLQuery<Board> query = from(board);   // select... from board

        query.where(board.title.contains("1")); // where title like ...

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if( (types != null && types.length >0) && keyword != null){     // 검색조건과 키워드가 있으면

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){

                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }// end for
            query.where(booleanBuilder);

        }// end if

        // bno > 0
        query.where(board.bno.gt(0L));

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }


}
