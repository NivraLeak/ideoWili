package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class SaveTopicResource {
    @NotNull
    @NotBlank
    private String Name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String bibliography;
}
