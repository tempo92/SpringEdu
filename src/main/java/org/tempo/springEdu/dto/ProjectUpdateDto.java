package org.tempo.springEdu.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProjectUpdateDto {
    @NotBlank(message = "The property 'name' is not defined")
    private String name;
}
