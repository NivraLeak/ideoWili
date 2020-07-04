package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveCategoryResource {
    @NotNull
    @Size(max = 100)
    @NaturalId///Llave alterna en bd
    private String name;
}
