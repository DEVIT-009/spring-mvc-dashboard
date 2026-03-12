package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Teacher;
import com.pos.dashboardmvc.repositories.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TeacherService {

    private final FileStorageService fileStorageService;
    private final TeacherRepository teacherRepository;
    private final String subFolder = "teachers/";

    public TeacherService(FileStorageService fileStorageService, TeacherRepository teacherRepository){
        this.fileStorageService = fileStorageService;
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> listAll()
    {
        return teacherRepository.findAll();
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public Teacher getTeacherById(int id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TEACHER_NOT_FOUND"));
    }

    public void create(Teacher formTeacher, MultipartFile image){
        if(image != null && !image.isEmpty()){
            String imagePath = fileStorageService.storeImage(image, "teachers/");
            formTeacher.setImagePath(imagePath);
        }else{
            formTeacher.setImagePath("");
        }

        teacherRepository.save(formTeacher);
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
        teacherRepository.save(teacher);
    }

    public void delete(int id) {
        Teacher teacher = this.getTeacherById(id);

        String imagePath = teacher.getImagePath();
        if(imagePath != null && !imagePath.isEmpty()){
            fileStorageService.deleteImage(imagePath, subFolder);
        }

        teacherRepository.delete(teacher);
    }
}
