package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Subject;
import com.pos.dashboardmvc.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> listAll()
    {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(int id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SUBJECT_NOT_FOUND"));
    }

    public void create(Subject subjectForm) {
        subjectRepository.save(subjectForm);
    }

    public void update(int id, Subject formSubject) {
        Subject subject = this.getSubjectById(id);
        subject.setSubjectName(formSubject.getSubjectName());
        subjectRepository.save(subject);
    }

    public void delete(int id) {
        Subject subject = this.getSubjectById(id);
        subjectRepository.delete(subject);
    }
}
