package com.pos.dashboardmvc.repositories;

import com.pos.dashboardmvc.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
