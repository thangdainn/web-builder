package org.dainn.funnelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.funnelservice.util.constant.PageConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageRequest {
    private Integer page = PageConstant.DEFAULT_PAGE;
    private Integer size = PageConstant.DEFAULT_PAGE_SIZE;
    private String sortBy = PageConstant.DEFAULT_SORT_BY;
    private String sortDir = PageConstant.DEFAULT_SORT_DIRECTION;
}
