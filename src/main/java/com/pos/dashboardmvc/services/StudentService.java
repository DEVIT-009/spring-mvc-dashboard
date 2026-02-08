package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Student;
import com.pos.dashboardmvc.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> data = new ArrayList<>();
    private int idCounter = 1;
    private final String subFolder = "students/";

    private final FileStorageService fileStorageService;

    public StudentService(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public List<Student> listAll() {
        return data;
    }

    public Student getStudentById(int id) {
        Student filterStudent = data.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);

        if(filterStudent == null){
            throw new RuntimeException("STUDENT_NOT_FOUND");
        }

        return filterStudent;
    }

    public void create(Student formStudent, MultipartFile image){
        formStudent.setId(idCounter++);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, subFolder);
            formStudent.setImagePath(imagePath);
        } else {
            formStudent.setImagePath("");
        }

        data.add(formStudent);
    }

    public void update(
            int id,
            Student formStudent,
            MultipartFile image
    ) {
        Student student = this.getStudentById(id);

        if(isNotEmpty(formStudent.getEmail())){
            student.setEmail(formStudent.getEmail());
        }
        if(isNotEmpty(formStudent.getFirstName())){
            student.setFirstName(formStudent.getFirstName());
        }
        if(isNotEmpty(formStudent.getLastName())){
            student.setLastName(formStudent.getLastName());
        }
        if(isNotEmpty(formStudent.getAddress())){
            student.setAddress(formStudent.getAddress());
        }
        if(isNotEmpty(formStudent.getSubjectName())){
            student.setSubjectName(formStudent.getSubjectName());
        }

        student.setDob(formStudent.getDob());
        student.setGender(formStudent.getGender());

        if(image != null && !image.isEmpty()){
            if (student.getImagePath() != null){
                fileStorageService.deleteImage(student.getImagePath(), subFolder);
            }
            student.setImagePath(fileStorageService.storeImage(image, subFolder));
        }
    }

    public void delete(int id) {
        Student student = this.getStudentById(id);

        String imagePath = student.getImagePath();
        if(imagePath != null && !imagePath.isEmpty()){
            fileStorageService.deleteImage(imagePath, subFolder);
        }

        data.remove(student);
    }
}
