package com.insurg.examinator.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.insurg.examinator.model.Exam;
import com.insurg.examinator.model.Question;
import com.insurg.examinator.model.Section;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExamController {

    private final RestTemplate restTemplate;

    @PostMapping("/exam")
    public Exam getExam(@RequestBody Map<String, Integer> spec) {
        List<Section> sections = spec.entrySet().stream()
            .map(this::getUrl)
            .map(url -> restTemplate.getForObject(url, Question[].class))
            .map(Arrays::asList)
            .map(Section::new)
            .toList();

        return Exam.builder()
            .title("EXAM")
            .sections(sections)
            .build();
    }

    private String getUrl(Map.Entry<String, Integer> entry) {
        return "http://%s/api/question?amount=%s".formatted(entry.getKey(), entry.getValue());
    }

}
