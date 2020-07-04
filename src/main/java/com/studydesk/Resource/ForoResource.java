package com.studydesk.Resource;

import com.studydesk.Model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForoResource extends AuditModel {
    private Long id;
    private String title;
    private String description;
    private String content;
}
