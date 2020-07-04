package com.studydesk.Resource;

import com.studydesk.Model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResource extends AuditModel {
    private Long id;
    private String text;

}
