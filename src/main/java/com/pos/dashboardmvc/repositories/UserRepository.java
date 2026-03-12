package com.pos.dashboardmvc.repositories;

import com.pos.dashboardmvc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
