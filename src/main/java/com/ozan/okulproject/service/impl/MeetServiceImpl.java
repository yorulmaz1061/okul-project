package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.logic.MeetDTO;
import com.ozan.okulproject.dto.users.StudentDetailsDTO;
import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.entity.logic.Meet;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.MeetRepository;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.MeetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class MeetServiceImpl implements MeetService {

    private final MeetRepository meetRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public MeetServiceImpl(MeetRepository meetRepository, UserRepository userRepository, MapperUtil mapperUtil) {
        this.meetRepository = meetRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public MeetDTO save(MeetDTO dto) {
        if (dto.getTeacher() == null || dto.getTeacher().getId() == null) {
            throw new OkulProjectException("Advisor teacher id is required.");
        }
        User teacher = userRepository.findById(dto.getTeacher().getId())
                .orElseThrow(() -> new OkulProjectException("Teacher not found: " + dto.getTeacher().getId()));

        if (teacher.getRole() != Role.TEACHER) {
            throw new OkulProjectException("Only TEACHER can be assigned as meet owner.");
        }
        if (teacher.getTeacherDetails() == null || !Boolean.TRUE.equals(teacher.getTeacherDetails().getIsAdvisor())) {
            throw new OkulProjectException("Meet can only be created by an advisor teacher.");
        }
        if (!teacher.isEnabled()) {
            throw new OkulProjectException("Teacher account is disabled.");
        }
        if (dto.getStudentDTOList() == null || dto.getStudentDTOList().isEmpty()) {
            throw new OkulProjectException("At least one student must be selected.");
        }
        List<Long> studentIds = dto.getStudentDTOList().stream()
                .map(StudentDetailsDTO::getId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        if (studentIds.isEmpty()) {
            throw new OkulProjectException("Student ids are required.");
        }
        List<User> students = userRepository.findAllById(studentIds);
        if (students.size() != studentIds.size()) {
            throw new OkulProjectException("Student/some students not found by given ids.");
        }
        boolean allStudentRole = students.stream().allMatch(u -> u.getRole() == Role.STUDENT && u.isEnabled());
        if (!allStudentRole) {
            throw new OkulProjectException("All participants in student list must be enabled users with STUDENT role.");
        }
        if (dto.getDate() == null || !dto.getDate().isAfter(LocalDate.now())) {
            throw new OkulProjectException("Meeting date must be in the future.");
        }
        Meet meet = mapperUtil.convert(dto, Meet.class);
        meet.setTeacher(teacher);
        meet.setStudentList(students);
        if(dto.getStartTime() != null) {
            meet.setEndTime(dto.getStartTime().plusMinutes(90));
        }
        Meet saved = meetRepository.save(meet);
        return mapperUtil.convert(saved, MeetDTO.class);
    }

    @Override
    public List<MeetDTO> listMeetByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(()->new OkulProjectException("User id not found: " + userId));
        List<Meet> meets = meetRepository.findAllForUser(userId);
        return meets.stream().map(m->{
            MeetDTO dto = mapperUtil.convert(m, MeetDTO.class);
            if (m.getTeacher() != null) {
                TeacherDetailsDTO t = new TeacherDetailsDTO();
                t.setId(m.getTeacher().getId());
                t.setIsAdvisor(m.getTeacher().getTeacherDetails() != null
                ? m.getTeacher().getTeacherDetails().getIsAdvisor() : null
                );
                dto.setTeacher(t);
            }
            if (m.getStudentList() != null) {
            dto.setStudentDTOList(
                    m.getStudentList().stream().map(s->{
                        StudentDetailsDTO sd = new StudentDetailsDTO();
                        sd.setId(s.getId());
                        return sd;
                    }).toList()
            );
            }
            return dto;
        }).toList();
    }

    @Override
    public void deleteMeet(Long meetId) {
        Meet meet = meetRepository.findByIdAndIsDeleted(meetId, false);


    }

}


