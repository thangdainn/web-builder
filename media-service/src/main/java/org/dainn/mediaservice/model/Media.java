package org.dainn.mediaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medias")
public class Media extends BaseEntity {
    private String name;
    private String type;
    private String link;
    private String subAccountId;
}
