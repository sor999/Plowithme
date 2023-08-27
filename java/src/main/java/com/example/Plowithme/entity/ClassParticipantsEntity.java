package com.example.Plowithme.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

@Entity
@Getter
@Setter
@Table(name = "class_participant_table")
public class ClassParticipantsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participant_id;

    @Column
    private Long userid;

    @Column
    private Long classid;

    /*@Column
    private Profile profileImageName;*/

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private ClassEntity classEntity;



    public static ClassParticipantsEntity toSaveParticiantEntity(ClassEntity classEntity,User user){
        ClassParticipantsEntity classParticipantsEntity = new ClassParticipantsEntity();

        classParticipantsEntity.setUserid(user.getId());
        /*classParticipantsEntity.setProfileImageName(user.getProfile_image());*/
        classParticipantsEntity.setClassid(classEntity.getId());
        classParticipantsEntity.setClassEntity(classEntity);
        return classParticipantsEntity;
    }
}