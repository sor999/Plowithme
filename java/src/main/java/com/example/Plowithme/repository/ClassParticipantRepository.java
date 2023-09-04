package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassParticipantRepository extends JpaRepository<ClassParticipantsEntity, Long> {
<<<<<<< HEAD
    Optional<ClassParticipantsEntity> deleteClassParticipantsEntityByUseridAndMeetingid(Long userid, Long id);
=======
    Optional<ClassParticipantsEntity> deleteClassParticipantsEntityByUseridAndMeetingid(Long userid, Long meetingid);


    Optional<ClassParticipantsEntity> findClassParticipantsEntityByUseridAndClassEntity(Long userid, ClassEntity classEntity);
>>>>>>> 10886c143c7237db73f7fa0ac475b5efa82090b2
    Optional<ClassParticipantsEntity> deleteClassParticipantsEntitiesByClassEntity(ClassEntity classEntity);
}
