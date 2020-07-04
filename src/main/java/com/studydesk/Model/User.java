package com.studydesk.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @NaturalId///Llave alterna en bd
    private String name;


    @NotNull
    @Size(max = 100)
    private String password;

    @OneToOne(mappedBy = "user")
    private Profile profile;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "users")///ve a posts tags y ahi esta la info, solo configuracion en la parte de muchos
    @JsonIgnore
    private List<Foro> foros;



}
