package com.insurg.examinator.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Exam {
    private String title;
    private List<Section> sections;
}
