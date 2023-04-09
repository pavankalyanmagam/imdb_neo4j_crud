package com.adb.prog.repository;

import com.adb.prog.model.Genres;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GenresRepository extends Neo4jRepository<Genres, Long> {
}
