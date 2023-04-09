package com.adb.prog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genres {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String type;
}
