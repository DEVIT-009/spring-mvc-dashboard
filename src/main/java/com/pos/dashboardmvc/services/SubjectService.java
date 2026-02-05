package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Subject;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    private final List<Subject> data = new ArrayList<>();
    private int idCounter = 1;

    public @Nullable Object listAll() {
        return data;
    }

    public void create(Subject subjectForm) {
        subjectForm.setId(idCounter++);
        data.add(subjectForm);
    }

}
