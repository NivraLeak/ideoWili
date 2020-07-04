package com.studydesk.Resource;

import com.studydesk.Model.AuditModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TeacherResource extends AuditModel {
    private Long id;
    private String name;
    private String field;
}
