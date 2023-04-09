package com.adb.prog.controller;

import com.adb.prog.model.*;
import com.adb.prog.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/imdb")
public class IMDBController {

    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private Neo4jTemplate neo4jTemplate;

    // 1.	Insert the new movie information.
    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movieDetails) {
        return movieRepository.save(movieDetails);
    }

    // 2. Update the movie information using title. (By update only title, description, and rating)
    @PutMapping("/movies/{title}")
    public Movie updateMovie(@PathVariable String title, @RequestBody Movie movie) {
        Optional<List<Movie>> optionalMovie = movieRepository.findByTitle(title);
        if (optionalMovie.isPresent() && !optionalMovie.get().isEmpty()) {
            List<Movie> movies = optionalMovie.get();
            for (Movie m : movies) {
                m.setTitle(movie.getTitle());
                m.setDescription(movie.getDescription());
                m.setRating(movie.getRating());
                neo4jTemplate.save(m);
            }
            return movies.get(0); // return the first movie in the list
        } else {
            throw new RuntimeException("Movie with title " + title + " not found");
        }
    }

    // 3.Delete the movie information using title.
    @DeleteMapping("/movies/{title}")
    public ResponseEntity<String> deleteMovie(@PathVariable("title")  String title) {
        Optional<List<Movie>> optionalMovie = movieRepository.findByTitle(title);
        if (optionalMovie.isPresent() && !optionalMovie.get().isEmpty()) {
            movieRepository.deleteByTitle(title);
            return new ResponseEntity<>(title + " Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Movie with title " + title + " not found", HttpStatus.NOT_FOUND);
        }
    }



    // 4.Retrieve all the movies in database.
    @GetMapping("/movies")
    public List<Movie> getMovies(){
       return movieRepository.findAll();
    }

    //5.	Display the movie’s details includes actors, directors and genres using title.
    @GetMapping("/movies/{title}")
    public Optional<List<MovieDetails>> getMovieByTitle(@PathVariable String title) {
        Optional<List<Movie>> byTitle = movieRepository.findByTitle(title);
        Optional<List<MovieDetails>> movieDetailsList = byTitle.map(movies -> movies.stream().map(this::mapToMovieDetails).collect(Collectors.toList()));
        return movieDetailsList;
    }


    private MovieDetails mapToMovieDetails(Movie movie) {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setIds(movie.getIds());
        movieDetails.setTitle(movie.getTitle());
        movieDetails.setDescription(movie.getDescription());
        movieDetails.setYear(movie.getYear());
        movieDetails.setRuntime(movie.getRuntime());
        movieDetails.setRating(movie.getRating());
        movieDetails.setVotes(movie.getVotes());
        movieDetails.setRevenue(movie.getRevenue());
        movieDetails.setActor(movie.getActor());
        movieDetails.setDirector(movie.getDirector());
        movieDetails.setGenre(movie.getGenre());
        return movieDetails;
    }
}
