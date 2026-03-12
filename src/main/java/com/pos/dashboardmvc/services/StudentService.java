package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Student;
import com.pos.dashboardmvc.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class StudentService {

    private final String subFolder = "students/";
    private final StudentRepository studentRepository;

    private final FileStorageService fileStorageService;

    public StudentService(StudentRepository studentRepository, FileStorageService fileStorageService){
        this.studentRepository = studentRepository;
        this.fileStorageService = fileStorageService;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public List<Student> listAll()
    {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id)
    {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("STUDENT_NOT_FOUND"));
    }

    public void create(Student formStudent, MultipartFile image){

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, subFolder);
            formStudent.setImagePath(imagePath);
        } else {
            formStudent.setImagePath("");
        }

        studentRepository.save(formStudent);
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

        studentRepository.save(student);
    }

    public void delete(int id) {
        Student student = this.getStudentById(id);

        String imagePath = student.getImagePath();
        if(imagePath != null && !imagePath.isEmpty()){
            fileStorageService.deleteImage(imagePath, subFolder);
        }

        studentRepository.delete(student);
    }
}
