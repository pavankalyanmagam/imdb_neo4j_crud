package com.adb.prog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieRequest {

    private String ids;

    private String title;

    private String description;

    private String year;

    private String runtime;

    private String rating;

    private String votes;

    private Integer revenue;

    private List<String> actor;

    private List<String> director;

    private List<String> genre;
}
