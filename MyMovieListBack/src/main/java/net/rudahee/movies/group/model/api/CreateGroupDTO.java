package net.rudahee.movies.group.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupDTO {

    private String name;
    private UUID creatorId;
}
