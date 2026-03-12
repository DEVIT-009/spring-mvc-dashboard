package com.pos.dashboardmvc.repositories;

import com.pos.dashboardmvc.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}