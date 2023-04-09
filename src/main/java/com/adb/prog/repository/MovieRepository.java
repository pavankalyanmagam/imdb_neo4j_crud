package com.adb.prog.repository;

import com.adb.prog.model.Movie;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends Neo4jRepository<Movie, Long> {

    @Query("MATCH (m:Movie)-[:DIRECTED]->(d:Person) "
            + "OPTIONAL MATCH (m)-[:ACTED_IN]->(a:Person) "
            + "OPTIONAL MATCH (m)-[:IN]->(g:Genres) "
            + "RETURN m, COLLECT(DISTINCT d) AS directors, "
            + "COLLECT(DISTINCT a) AS actors, COLLECT(DISTINCT g) AS genres")
    List<Movie> findAllWithDirectorsActorsGenres();

    Optional <List<Movie>> findByTitle(String title);

    void deleteByTitle(String title);

    
}
