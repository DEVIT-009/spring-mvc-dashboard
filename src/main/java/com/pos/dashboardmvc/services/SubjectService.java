package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Subject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    private final List<Subject> data = new ArrayList<>();
    private int idCounter = 1;

    public List<Subject> listAll() {
        return data;
    }

    public Subject getSubjectById(int id) {
        Subject filterSubject = data.stream()
                .filter(subject -> subject.getId() == id)
                .findFirst()
                .orElse(null);

        if(filterSubject == null){
            throw new RuntimeException("SUBJECT_NOT_FOUND");
        }

        return filterSubject;
    }

    public void create(Subject subjectForm) {
        subjectForm.setId(idCounter++);
        data.add(subjectForm);
    }

    public void update(int id, Subject formSubject) {
        Subject subject = this.getSubjectById(id);
        subject.setSubjectName(formSubject.getSubjectName());
    }

    public void delete(int id) {
        Subject subject = this.getSubjectById(id);
        data.remove(subject);
    }
}
