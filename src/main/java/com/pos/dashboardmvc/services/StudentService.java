package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Student;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> data = new ArrayList<>();
    private int idCounter = 1;
    private RedirectAttributes redirectAttributes;

    private final FileStorageService fileStorageService;

    public StudentService(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    public List<Student> listAll() {
        return data;
    }

    public void create(Student formStudent, MultipartFile image){
        formStudent.setId(idCounter++);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, "students/");
            formStudent.setImagePath(imagePath);
        } else {
            formStudent.setImagePath("");
        }

        data.add(formStudent);
    }
}
