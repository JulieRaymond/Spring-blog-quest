package com.myblog.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagDTO {
    private Long id;
    private String name;
    private List<Long> articleIds;
}
