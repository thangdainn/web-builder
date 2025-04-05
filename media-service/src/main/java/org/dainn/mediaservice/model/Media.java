package org.dainn.mediaservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medias")
public class Media extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String type;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String subAccountId;
}
