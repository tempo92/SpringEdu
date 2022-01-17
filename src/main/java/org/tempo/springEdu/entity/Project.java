package org.tempo.springEdu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class project {
    @Id
    private String id;
    private String name;
}
