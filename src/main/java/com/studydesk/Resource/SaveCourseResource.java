package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveCourseResource {
    @NotNull
    @NotBlank
    @Size(max=100)
    @Column(unique = true)
    private String title;
}
