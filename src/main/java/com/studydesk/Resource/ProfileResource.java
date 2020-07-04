package com.studydesk.Resource;

import com.studydesk.Model.AuditModel;
import com.studydesk.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResource extends AuditModel {
    private Long id;
    private  String name;
    private  String sex;
    private String FirstName;
    private String LastName;


}
