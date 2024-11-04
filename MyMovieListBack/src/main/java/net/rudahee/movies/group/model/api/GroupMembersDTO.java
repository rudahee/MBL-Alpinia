package net.rudahee.movies.group.model.api;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembersDTO {
    private String name;
    private List<String> members;
}
