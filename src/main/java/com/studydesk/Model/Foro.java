package com.studydesk.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "foro")
@Getter
@Setter
public class Foro  extends  AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @NotBlank
    @Size(max=100)
    @Column(unique = true)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 250)
    @Column(unique=true)
    private String description;

    @NotNull
    @Lob
    private String content;

    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST,CascadeType.MERGE})
                      @JoinTable(name = "foro_tags",
                      joinColumns = {@JoinColumn(name="foro_id")},
                      inverseJoinColumns = {@JoinColumn(name = "tag_id")})
                      @JsonIgnore
    List<Tag> tags;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "foro_users",
            joinColumns = {@JoinColumn(name="foro_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @JsonIgnore
    List<User> users;


}
