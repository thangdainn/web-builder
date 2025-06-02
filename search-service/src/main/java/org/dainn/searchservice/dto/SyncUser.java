package org.dainn.searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.searchservice.document.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SyncUser {
    private User user;
    private boolean updatePer;
}
