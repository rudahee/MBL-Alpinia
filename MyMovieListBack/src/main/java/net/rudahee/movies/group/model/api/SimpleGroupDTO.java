package net.rudahee.movies.group.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGroupDTO {

    private UUID id;
    private String name;
}
