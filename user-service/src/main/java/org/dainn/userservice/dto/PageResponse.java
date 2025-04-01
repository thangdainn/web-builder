package org.dainn.userservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private Integer page;
    private Integer size;
    private long totalElements;
    private List<T> data = new ArrayList<>();
}
