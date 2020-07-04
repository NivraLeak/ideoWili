package com.studydesk.Resource;

import com.studydesk.Model.AuditModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TopicResource extends AuditModel {
    private Long id;

    private String Name;

    private String description;

    private String bibliography;
}
