package com.testvito.backendtestvito.controller;

import com.testvito.backendtestvito.dto.CreateGroupRequest;
import com.testvito.backendtestvito.dto.GroupResponse;
import com.testvito.backendtestvito.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @RequestBody CreateGroupRequest request) {

        GroupResponse response = groupService.createGroup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}