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

    public TeacherService(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    public List<Teacher> listAll(){
        return data;
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

}
