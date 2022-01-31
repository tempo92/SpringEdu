package org.tempo.springEdu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Project {
    @Id
    private String id;
    private String name;
    private String ownerId;

    public void setOwnerId(String ownerId) {
        if (this.ownerId != null) {
            throw new RuntimeException("ownerId cannot be changed");
        }
        this.ownerId = ownerId;
    }
}
