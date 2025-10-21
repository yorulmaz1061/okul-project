package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.logic.MeetDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.service.MeetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/meets")
@Tag(name = "MeetController", description = "Meet API")
public class MeetController {
    private final MeetService meetService;

    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }
    @ExecutionTime
    @PostMapping
    @RolesAllowed({"Teacher"})
    @Operation(summary = "Create a meet")
    public ResponseEntity<ResponseWrapper> createMeet(@RequestBody MeetDTO dto){
        MeetDTO saved = meetService.save(dto);
        return ResponseEntity.ok(new ResponseWrapper("Meet is successfully created",saved, HttpStatus.CREATED));
    }

    @ExecutionTime
    @GetMapping("/{userId}")
    @RolesAllowed({"Admin","Teacher","Student"})
    @Operation(summary = "Get meet by user Id (teacher or student)")
    public ResponseEntity<ResponseWrapper> getMeetById(@PathVariable("userId") Long userId){
        List<MeetDTO> list = meetService.listMeetByUser(userId);
        String msg = list.isEmpty() ? "No meets found for user " + userId :"Meet is successfully retrieved";
        return ResponseEntity.ok(new ResponseWrapper(msg,list, HttpStatus.OK));
    }

    @ExecutionTime
    @DeleteMapping("/{meetId}")
    @RolesAllowed({"Teacher"})
    @Operation(summary = "Delete meet by meet Id")
    public ResponseEntity<ResponseWrapper> deleteMeetById(@PathVariable("meetId") Long meetId){
        meetService.deleteMeet(meetId);
        return ResponseEntity.ok(new ResponseWrapper("Meet is successfully deleted",HttpStatus.OK));
    }


}
