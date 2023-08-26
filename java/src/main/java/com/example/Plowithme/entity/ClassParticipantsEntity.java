package com.example.Plowithme.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long user_id;

    /*@Column
    private Profile profileImageName;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private ClassEntity classEntity;



    public static ClassParticipantsEntity toSaveEntity(ClassEntity classEntity,User user){
        ClassParticipantsEntity classParticipantsEntity = new ClassParticipantsEntity();

        classParticipantsEntity.setUser_id(user.getId());
        /*classParticipantsEntity.setProfileImageName(user.getProfile_image());*/
        classParticipantsEntity.setClassEntity(classEntity);
        return classParticipantsEntity;
    }
}