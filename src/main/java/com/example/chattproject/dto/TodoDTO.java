package com.example.chattproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data // getter/setter/toString/equals/hashCode 등 모두 컴파일할 때 생성해줌
@NoArgsConstructor
@AllArgsConstructor

public class TodoDTO {

    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;
}
