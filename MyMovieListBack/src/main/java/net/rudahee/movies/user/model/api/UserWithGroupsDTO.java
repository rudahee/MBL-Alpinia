package net.rudahee.movies.user.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.group.model.api.GroupDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithGroupsDTO {
    private String name;
    private List<GroupDTO> groups;
}
