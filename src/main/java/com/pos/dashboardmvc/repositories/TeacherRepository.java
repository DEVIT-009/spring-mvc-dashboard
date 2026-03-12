package com.pos.dashboardmvc.repositories;

import com.pos.dashboardmvc.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
