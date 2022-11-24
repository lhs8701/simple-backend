package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnitUpdateParam {
    @NotNull
    private String title;
    private String description;
    private String objective;
}
