package net.rudahee.movies.group.controllers;

import jakarta.servlet.http.HttpServletRequest;
import net.rudahee.movies.group.model.api.CreateGroupDTO;
import net.rudahee.movies.group.model.api.GetGroupWithMembersAndRatingsDTO;
import net.rudahee.movies.group.model.api.GroupDTO;
import net.rudahee.movies.group.model.api.GroupMembersDTO;
import net.rudahee.movies.group.service.GroupService;
import net.rudahee.movies.shared.exceptions.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupDTO dto) throws APIException {
        GroupDTO groupDTO = groupService.createGroup(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(groupDTO);
    }

    @PutMapping("/{inviteCode}")
    public ResponseEntity<GroupDTO> joinUserToGroup(HttpServletRequest request, @PathVariable String inviteCode) throws APIException {
        Principal user = request.getUserPrincipal();
        GroupDTO dto = groupService.joinMemberByPrincipal(inviteCode, user);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value="/{id}/members")
    public ResponseEntity<GroupMembersDTO> getMembersFromGroup(@PathVariable UUID id) throws APIException {
        GroupMembersDTO dto = groupService.getGroupMembers(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value="/members")
    public ResponseEntity<List<GroupMembersDTO>> getMembersFromAllGroupFromUser(@RequestParam("id") UUID userId) {
        List<GroupMembersDTO> dtos = groupService.getAllGroupsWithMembers(userId);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetGroupWithMembersAndRatingsDTO> getGroupWithMembersAndRatings(@PathVariable UUID id) {
        GetGroupWithMembersAndRatingsDTO dto = groupService.getGroupWithMembersAndRatings(id);

        return ResponseEntity.ok(dto);
    }

}
