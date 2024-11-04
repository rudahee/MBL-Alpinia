package net.rudahee.movies.group.model.api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private UUID id;
    private String name;
    private String inviteCode;
    private Map<UUID, String> users;

    public GroupDTO (UUID id, String name) {
        this.name = name;
        this.id = id;
    }
}
