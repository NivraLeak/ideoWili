package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveTeacherResource {
    @NotNull
    @Size(max = 100)
    @NaturalId
    private String name;

    @NotNull
    @NotBlank
    private String field;
}
