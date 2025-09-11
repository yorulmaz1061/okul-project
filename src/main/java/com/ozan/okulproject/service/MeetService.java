package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.logic.MeetDTO;

import java.util.List;

public interface MeetService {
    MeetDTO save(MeetDTO dto);
    List<MeetDTO> listMeetByUser(Long userId);
    void deleteMeet(Long meetId);

}
