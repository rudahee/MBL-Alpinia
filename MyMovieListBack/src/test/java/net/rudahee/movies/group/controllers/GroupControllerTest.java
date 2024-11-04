package net.rudahee.movies.group.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import net.rudahee.movies.group.model.api.CreateGroupDTO;
import net.rudahee.movies.group.model.api.GetGroupWithMembersAndRatingsDTO;
import net.rudahee.movies.group.model.api.GroupDTO;
import net.rudahee.movies.group.model.api.GroupMembersDTO;
import net.rudahee.movies.group.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createGroupShouldReturnCreatedGroup() throws Exception {
        CreateGroupDTO createGroupDTO = new CreateGroupDTO();
        createGroupDTO.setName("New Group");
        createGroupDTO.setCreatorId(UUID.randomUUID());

        GroupDTO groupDTO = new GroupDTO(UUID.randomUUID(), "New Group", "abcd", null);

        when(groupService.createGroup(createGroupDTO)).thenReturn(groupDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGroupDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Group"));

        verify(groupService).createGroup(createGroupDTO);
    }

    @Test
    void getMembersFromAllGroupFromUserShouldReturnAllGroups() throws Exception {
        UUID userId = UUID.randomUUID();
        GroupMembersDTO groupMembersDTO1 = new GroupMembersDTO("Group 1", List.of("user1"));
        GroupMembersDTO groupMembersDTO2 = new GroupMembersDTO("Group 2", List.of("user2"));
        List<GroupMembersDTO> groupMembersDTOS = List.of(groupMembersDTO1, groupMembersDTO2);

        when(groupService.getAllGroupsWithMembers(userId)).thenReturn(groupMembersDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/members?id=" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Group 1"))
                .andExpect(jsonPath("$[1].name").value("Group 2"));

        verify(groupService).getAllGroupsWithMembers(userId);
    }

    @Test
    void getGroupWithMembersAndRatingsShouldReturnGroupWithRatings() throws Exception {
        UUID groupId = UUID.randomUUID();
        GetGroupWithMembersAndRatingsDTO dto = new GetGroupWithMembersAndRatingsDTO(groupId, "Test Group", "abcd", null);

        when(groupService.getGroupWithMembersAndRatings(groupId)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/" + groupId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Group"));

        verify(groupService).getGroupWithMembersAndRatings(groupId);
    }
}
