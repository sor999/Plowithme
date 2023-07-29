package com.example.Plowithme.Repository;

import com.example.Plowithme.Entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    //등록
    public User save(User user) {
        log.info("save: user={}", user);
        em.persist(user);
        return user;
    }

    //단건 조회
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    //리스트 조회
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }
    //이메일 조회
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

//    //삭제
//    public User delete(User user){
//        em.remove(user);
//        return user;
//    }

}
