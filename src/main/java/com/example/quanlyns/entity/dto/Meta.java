package com.example.quanlyns.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private int page;
    private int sizePage;
    private int pages;
    private Long total;

}