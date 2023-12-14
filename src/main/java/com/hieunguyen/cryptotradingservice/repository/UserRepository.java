package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
