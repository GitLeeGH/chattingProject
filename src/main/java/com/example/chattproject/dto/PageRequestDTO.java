package com.example.chattproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *  페이징 관련 정보(page/size) 외에 검색의 종류(type)와 키워드(keyword)를 추가 지정
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;    // 검색의 종류 t,c,w,tc,tw,twc

    private String keyword;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return  null;
        }

        return type.split("");
    }

    // paging 처리 위해서
    public Pageable getPageable(String...props){

        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    // 검색 조건과 페이징 조건 등을 문자열로 구성 getLink()
    private String link;

    public String getLink() {

        if (link == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);

            builder.append("&size=" + this.size);


            if (type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }

            if (keyword != null) {
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {

                }
            }
            link = builder.toString();
        }

        return link;
    }
}
