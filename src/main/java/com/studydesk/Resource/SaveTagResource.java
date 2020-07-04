package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveTagResource {
    @NotNull
    @Size(max = 100)
    private String name;
}
