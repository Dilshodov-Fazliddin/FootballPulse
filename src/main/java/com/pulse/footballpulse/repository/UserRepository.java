package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByMail(String mail);
    Optional<UserEntity>findByMailAndCode(String mail, Integer code);
    boolean existsByMail(String mail);

    List<UserEntity> searchByMail(String mail);

    List<UserEntity> getByMail(String mail);
}
