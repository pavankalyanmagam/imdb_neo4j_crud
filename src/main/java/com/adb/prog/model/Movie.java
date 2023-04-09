package com.adb.prog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Property("Runtime")
    private Integer runtime;

    @Property("Description")
    private String description;

    @Property("Year")
    private String year;

    @Property("Rating")
    private String rating;

    @Property("Title")
    private String title;

    @Property("Votes")
    private String votes;

    @Property("Ids")
    private String ids;

    @Property("Revenue")
    private Integer revenue;

    @Property("Actors")
    private String actor;


    @Property("Genre")
    private String genre;

    @Property("Director")
    private String director;

    @Relationship(type = "IN")
    @JsonIgnore
    private List<Genres> genres;
    @Relationship(type = "ACTED_IN")
    @JsonIgnore
    private List<Person> actors;
    @Relationship(type = "DIRECTED")
    @JsonIgnore
    private List<Person> directors;

}
