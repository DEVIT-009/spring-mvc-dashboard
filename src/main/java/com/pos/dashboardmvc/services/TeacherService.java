package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    private final List<Teacher> data = new ArrayList<>();
    private final FileStorageService fileStorageService;
    private int idCounter = 1;
    private final String subFolder = "teachers/";

    public TeacherService(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    public List<Teacher> listAll(){
        return data;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public Teacher getTeacherById(int id) {
        Teacher filterTeacher = data.stream()
                .filter(teacher -> teacher.getId() == id)
                .findFirst()
                .orElse(null);

        if(filterTeacher == null) {
            throw new RuntimeException("STUDENT_NOT_FOUND");
        }

        return filterTeacher;
    }

    public void create(Teacher formTeacher, MultipartFile image){
        formTeacher.setId(idCounter++);

        if(image != null && !image.isEmpty()){
            String imagePath = fileStorageService.storeImage(image, "teachers/");
            formTeacher.setImagePath(imagePath);
        }else{
            formTeacher.setImagePath("");
        }

        data.add(formTeacher);
    }


    public void update(
            int id,
            Teacher formTeacher,
            MultipartFile image
    ) {
        Teacher teacher = this.getTeacherById(id);

        if(formTeacher.getSalary() == 0.00){
            teacher.setSalary(formTeacher.getSalary());
        }
        if(isNotEmpty(formTeacher.getPhone())){
            teacher.setPhone(formTeacher.getPhone());
        }
        if(isNotEmpty(formTeacher.getFirstName())){
            teacher.setFirstName(formTeacher.getFirstName());
        }
        if(isNotEmpty(formTeacher.getLastName())){
            teacher.setLastName(formTeacher.getLastName());
        }
        if(isNotEmpty(formTeacher.getAddress())){
            teacher.setAddress(formTeacher.getAddress());
        }
        if(isNotEmpty(formTeacher.getSubjectName())){
            teacher.setSubjectName(formTeacher.getSubjectName());
        }

        teacher.setDob(formTeacher.getDob());
        teacher.setGender(formTeacher.getGender());

        if(image != null && !image.isEmpty()){
            if (teacher.getImagePath() != null){
                fileStorageService.deleteImage(teacher.getImagePath(), subFolder);
            }
            teacher.setImagePath(fileStorageService.storeImage(image, subFolder));
        }
    }

    public void delete(int id) {
        Teacher teacher = this.getTeacherById(id);

        String imagePath = teacher.getImagePath();
        if(imagePath != null && !imagePath.isEmpty()){
            fileStorageService.deleteImage(imagePath, subFolder);
        }

        data.remove(teacher);
    }
}
