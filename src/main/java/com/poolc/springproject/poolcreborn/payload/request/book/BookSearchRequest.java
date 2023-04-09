package com.poolc.springproject.poolcreborn.payload.request.book;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Builder(toBuilder = true)
@Getter
@ToString
public class BookSearchRequest {

    @NotEmpty
    private String query;
    private Integer display;
    private Integer start;
    private String sort;
    private String d_title;
    private String d_isbn;

}
