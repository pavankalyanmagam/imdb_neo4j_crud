package com.adb.prog.controller;

import com.adb.prog.model.Genres;
import com.adb.prog.model.Movie;
import com.adb.prog.model.Person;
import com.adb.prog.repository.GenresRepository;
import com.adb.prog.repository.MovieRepository;
import com.adb.prog.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imdb")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GenresRepository genresRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    // 1.	Insert the new movie information.
    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        Genres genre = new Genres();
        genre.setType(movie.getGenre());
        movie.setGenres(Collections.singletonList(genre));

        List<Person> actors = new ArrayList<>();
        String[] actorNames = movie.getActor().split(", ");
        for (String actorName : actorNames) {
            Person actor = new Person();
            actor.setName(actorName);
            actors.add(actor);
        }
        movie.setActors(actors);

        Person director = new Person();
        director.setName(movie.getDirector());
        movie.setDirectors(Collections.singletonList(director));

        return neo4jTemplate.save(movie);
    }


    // 2. Update the movie information using title. (By update only title, description, and rating)
    @PutMapping("/movies/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id);
        neo4jTemplate.save(movie);
        return movie;
    }

    // 3.Delete the movie information using title.
    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // 4.Retrieve all the movies in database.
    @GetMapping("/movies")
    public List<Movie> getMovies(){
       return movieRepository.findAll();
    }

    //5.	Display the movie’s details includes actors, directors and genres using title.
    @GetMapping("/movies/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        Optional<Movie> movieOptional = movieRepository.findByTitle(title);
        if (movieOptional.isPresent()) {
            return ResponseEntity.ok(movieOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
