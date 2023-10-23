package com.construo.ch.fruitsvegetables.util;

import org.springframework.data.domain.*;

import java.util.List;

public class PaginationHelper {

    public <T> Page<T> createPage(List<T> content, int pageIndex, int pageSize){
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        return new PageImpl<>(content, pageable, content.size());
    }

    public <T> PageRequest createPageRequest(List<T> content, int pageIndex, int pageSize, String sortBy){
        return PageRequest.of(pageIndex, pageSize, Sort.by(sortBy));
    }
}
