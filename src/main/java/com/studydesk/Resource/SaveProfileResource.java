package com.studydesk.Resource;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveProfileResource {
    @NotNull
    @NotBlank
    @Size(max=30)
    private  String name;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private  String sex;

    @NotNull
    @NotBlank
    @Size(max=30)
    private String FirstName;

    @NotNull
    @NotBlank
    @Size(max=30)
    private String LastName;
}
