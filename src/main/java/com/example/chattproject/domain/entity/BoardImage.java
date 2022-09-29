package com.example.chattproject.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
// Comparable 인터페이스 사용 => @OneToMany 처리에서 순번에 맞게 정렬하기 위해서
public class BoardImage implements Comparable<BoardImage>{

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }

    public void changeBoard(Board board) {
        this.board = board;
    }
}
