package com.pos.dashboardmvc.controllers;

import com.pos.dashboardmvc.models.Sample;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sample")
public class SampleController {
    @GetMapping("")
    public String index(Model model){
        List<Sample> samples = new ArrayList<>();
        Sample _sample = new Sample();
        Sample __sample = new Sample();
        Sample ___sample = new Sample();

        _sample.setId(1);
        _sample.setSampleName("BMW");
        _sample.setSampleStatus(true);

        __sample.setId(2);
        __sample.setSampleName("Lamborghini");
        __sample.setSampleStatus(false);

        ___sample.setId(3);
        ___sample.setSampleName("Ferrari");
        ___sample.setSampleStatus(true);

        samples.add(_sample);
        samples.add(__sample);
        samples.add(___sample);

        model.addAttribute("samples", samples);

        Sample ____sample = new Sample();
        ____sample.setId(4);
        ____sample.setSampleName("Mercedes");
        ____sample.setSampleStatus(true);

        model.addAttribute("sample", ____sample);

        return "sample";
    }

    @GetMapping("/layout")
    public String layout(Model model){
        return "sample_layout";
    }

    @GetMapping("/layout/content_v1")
    public String contentV1(){
        return "sample_content/content_v1";
    }

    @GetMapping("/layout/content_v2")
    public String contentV2(){
        return "sample_content/content_v2";
    }
}
