package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.logic.EducationTermDTO;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.dto.users.TeacherQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.entity.logic.EducationTerm;
import com.ozan.okulproject.entity.logic.Lesson;
import com.ozan.okulproject.entity.logic.LessonSchedule;
import com.ozan.okulproject.enums.Day;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.EducationTermRepository;
import com.ozan.okulproject.repository.LessonRepository;
import com.ozan.okulproject.repository.LessonScheduleRepository;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.LessonService;
import com.ozan.okulproject.service.TeacherService;
import com.ozan.okulproject.service.UserService;
import org.hibernate.LazyInitializationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {
    private final MapperUtil mapperUtil;
    private final LessonRepository lessonRepository;
    private final EducationTermRepository educationTermRepository;
    private final UserRepository userRepository;
    private final LessonScheduleRepository lessonScheduleRepository;
    private final LessonService lessonService;

    public LessonServiceImpl(MapperUtil mapperUtil, LessonRepository lessonRepository, EducationTermRepository educationTermRepository, UserRepository userRepository, LessonScheduleRepository lessonScheduleRepository, @Lazy LessonService lessonService) {
        this.mapperUtil = mapperUtil;
        this.lessonRepository = lessonRepository;
        this.educationTermRepository = educationTermRepository;
        this.userRepository = userRepository;
        this.lessonScheduleRepository = lessonScheduleRepository;
        this.lessonService = lessonService;
    }


    @Override
    public LessonDTO findById(Long id) throws OkulProjectException {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, id);
        if (lesson == null) {
            throw new OkulProjectException("No lesson is found with that id: " + id);
        }
        LessonDTO dto = mapperUtil.convert(lesson, LessonDTO.class);
        if (lesson.getEducationTerm() != null) {
            EducationTerm term = lesson.getEducationTerm();
            dto.setEducationTermId(mapperUtil.convert(term, EducationTermDTO.class));
            dto.setTermLabel(term.getTermLabel());
            EducationTermDTO edDto = dto.getEducationTermId();
            if (term.getStartDate() != null) edDto.setStartDate(term.getStartDate());
            if (term.getEndDate() != null) edDto.setEndDate(term.getEndDate());
            if (term.getLastRegistrationDate() != null) edDto.setLastRegistrationDate(term.getLastRegistrationDate());
        }
        if (lesson.getTeacher() != null) {
            User teacher = lesson.getTeacher();
            String teacherInfo = teacher.getId() + " - " + teacher.getFirstName() + " - " + teacher.getLastName();
            dto.setAssignedTeacherInformation(teacherInfo);
            dto.setIsTeacherAssigned(true);
        } else {
            dto.setIsTeacherAssigned(false);
        }
        return dto;
    }

    @Override
    public List<LessonDTO> listAllLessons() {
        List<Lesson> lessonList = lessonRepository.findAllByIsDeleted(false);
        return lessonList.stream().map(lesson -> {
            LessonDTO dto = mapperUtil.convert(lesson, LessonDTO.class);
            if (lesson.getEducationTerm() != null) {
                EducationTerm term = lesson.getEducationTerm();
                dto.setEducationTermId(mapperUtil.convert(term, EducationTermDTO.class));
                dto.setTermLabel(term.getTermLabel());
                EducationTermDTO edDto = dto.getEducationTermId();
                if (term.getStartDate() != null) edDto.setStartDate(term.getStartDate());
                if (term.getEndDate() != null) edDto.setEndDate(term.getEndDate());
                if (term.getLastRegistrationDate() != null) edDto.setLastRegistrationDate(term.getLastRegistrationDate());
            }
            if (lesson.getTeacher() != null) {
                User teacher = lesson.getTeacher();
                String teacherInfo = teacher.getId() + " - " + teacher.getFirstName() + " - " + teacher.getLastName();
                dto.setAssignedTeacherInformation(teacherInfo);
                dto.setIsTeacherAssigned(true);
            } else {
                dto.setIsTeacherAssigned(false);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public LessonDTO deleteById(Long id) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, id);
        lesson.setIsDeleted(true);
        lessonRepository.save(lesson);
        return mapperUtil.convert(lesson, LessonDTO.class);

    }

    @Override
    @Transactional
    public LessonDTO save(LessonDTO dto) {
        Lesson lesson = mapperUtil.convert(dto, Lesson.class);
        lesson.setId(null);
        lesson.setTeacher(null);
        lesson.setEducationTerm(null);
        if (dto.getTeacherId() != null && dto.getTeacherId().getId() != null) {
            User teacher = userRepository.findById(dto.getTeacherId().getId())
                    .orElseThrow(() -> new OkulProjectException(
                            "Teacher not found with id: " + dto.getTeacherId().getId()));
            lesson.setTeacher(teacher);
            lesson.setIsTeacherAssigned(true);
        } else {
            lesson.setIsTeacherAssigned(false);
        }
        if (dto.getEducationTermId() == null || dto.getEducationTermId().getId() == null) {
            throw new OkulProjectException("Please select education term id");
        }
        EducationTerm term = educationTermRepository.findById(dto.getEducationTermId().getId())
                .orElseThrow(() -> new OkulProjectException(
                        "EducationTerm not found with id: " + dto.getEducationTermId().getId()));
        lesson.setEducationTerm(term);
        Lesson saved = lessonRepository.save(lesson);
        LessonDTO res = mapperUtil.convert(saved, LessonDTO.class);

        if (saved.getTeacher() != null) {
            String teacherInfo = saved.getTeacher().getId()
                    + " - " + saved.getTeacher().getFirstName()
                    + " - " + saved.getTeacher().getLastName();
            res.setAssignedTeacherInformation(teacherInfo);
            res.setIsTeacherAssigned(true);
        } else {
            res.setIsTeacherAssigned(false);
        }
        if (saved.getEducationTerm() != null) {
            if (res.getEducationTermId() != null) {
                res.getEducationTermId().setId(saved.getEducationTerm().getId());
            }
            res.setTermLabel(saved.getEducationTerm().getTermLabel());
        }
        res.setTotalStudentsCounts(
                saved.getStudentLessonInfo() == null ? "0" : String.valueOf(saved.getStudentLessonInfo().size())
        );

        return res;
    }


    @Override
    public LessonDTO updateLesson(Long id, LessonDTO dto) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, id);
        if (lesson == null) {
            throw new OkulProjectException("Lesson not found with id: " + id);
        }
        lesson.setLessonName(dto.getLessonName());
        lesson.setLessonCode(dto.getLessonCode());
        lesson.setCreditScore(dto.getCreditScore());
        lesson.setIsMandatory(dto.getIsMandatory());
        if (dto.getTeacherId() != null && dto.getTeacherId().getId() != null) {
            User teacher = userRepository.findById(dto.getTeacherId().getId())
                    .orElseThrow(() -> new OkulProjectException(
                            "Teacher not found with id: " + dto.getTeacherId().getId()
                    ));
            lesson.setTeacher(teacher);
            lesson.setIsTeacherAssigned(true);
        } else {
            lesson.setTeacher(null);
            lesson.setIsTeacherAssigned(false);
        }
        if (dto.getEducationTermId() != null && dto.getEducationTermId().getId() != null) {
            EducationTerm term = educationTermRepository.findById(dto.getEducationTermId().getId())
                    .orElseThrow(() -> new OkulProjectException(
                            "EducationTerm not found with id: " + dto.getEducationTermId().getId()
                    ));
            lesson.setEducationTerm(term);
        } else {
            throw new OkulProjectException("Please select education term id");
        }
        Lesson savedLesson = lessonRepository.save(lesson);
        LessonDTO responseDto = mapperUtil.convert(savedLesson, LessonDTO.class);
        if (savedLesson.getTeacher() != null) {
            String teacherInfo = savedLesson.getTeacher().getId()
                    + " - " + savedLesson.getTeacher().getFirstName()
                    + " - " + savedLesson.getTeacher().getLastName();
            responseDto.setAssignedTeacherInformation(teacherInfo);
        }
        if (savedLesson.getEducationTerm() != null) {
            responseDto.setTermLabel(savedLesson.getEducationTerm().getTermLabel());
        }
        if (savedLesson.getStudentLessonInfo() != null) {
            responseDto.setTotalStudentsCounts(
                    String.valueOf(savedLesson.getStudentLessonInfo().size())
            );
        }
        return responseDto;
    }

    @Override
    @Transactional
    public void saveLessonSchedule(LessonScheduleDTO dto) {
        LocalTime start = dto.getStartTime();
        LocalTime end   = start.plusHours(2);
        lessonService.findById(dto.getLesson().getId());
        Lesson lessonRef = lessonRepository.getReferenceById(dto.getLesson().getId());
        List<LessonSchedule> sameLessonSchedules =
                lessonScheduleRepository.findAllByLesson_IdAndIsDeletedFalse(lessonRef.getId());

        boolean lessonOverlap = sameLessonSchedules.stream().anyMatch(existing ->
                hasDayIntersection(existing.getDayList(), dto.getDayList())
                        && overlaps(start, end, existing.getStartTime(), existing.getEndTime())
        );
        if (lessonOverlap) {
            throw new OkulProjectException(
                    "Schedule conflict: Lesson(id=" + lessonRef.getId() + ") already has a slot on at least one of "
                            + dto.getDayList() + " between " + start + "-" + end
            );
        }
        if (lessonRef.getTeacher() != null) {
            Long teacherId = lessonRef.getTeacher().getId();
            List<LessonSchedule> teacherSchedules =
                    lessonScheduleRepository.findAllByLesson_Teacher_IdAndIsDeletedFalse(teacherId);
            boolean teacherOverlap = teacherSchedules.stream().anyMatch(existing ->
                    hasDayIntersection(existing.getDayList(), dto.getDayList())
                            && overlaps(start, end, existing.getStartTime(), existing.getEndTime())
            );
            if (teacherOverlap) {
                throw new OkulProjectException(
                        "Schedule conflict: Teacher(id=" + teacherId + ") has another lesson on at least one of "
                                + dto.getDayList() + " between " + start + "-" + end
                );
            }
        }
        LessonSchedule ls = new LessonSchedule();
        ls.setLesson(lessonRef);
        ls.setDayList(new ArrayList<>(dto.getDayList()));
        ls.setStartTime(start);
        ls.setEndTime(end);
        lessonScheduleRepository.save(ls);
    }
    @Override
    public LessonScheduleDTO findLessonScheduleById(Long id) {
        LessonSchedule ls = lessonScheduleRepository.findById(id)
                .orElseThrow(() -> new OkulProjectException("Lesson schedule not found with id: " + id));
        LessonScheduleDTO dto = mapperUtil.convert(ls, LessonScheduleDTO.class);
        if (ls.getLesson() != null) {
            if (dto.getLesson() == null) dto.setLesson(new LessonDTO());
            dto.getLesson().setId(ls.getLesson().getId());
            if (ls.getLesson().getEducationTerm() != null) {
                EducationTerm term = ls.getLesson().getEducationTerm();
                if (dto.getLesson().getEducationTermId() == null) {
                    dto.getLesson().setEducationTermId(new EducationTermDTO());
                }
                EducationTermDTO etDto = dto.getLesson().getEducationTermId();
                etDto.setId(term.getId());
                etDto.setTermLabel(term.getTermLabel());
                dto.getLesson().setTermLabel(term.getTermLabel());
            }
        }
        return dto;
    }



    private boolean overlaps(LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
        return aStart.isBefore(bEnd) && aEnd.isAfter(bStart);
    }

    private boolean hasDayIntersection(List<Day> a, List<Day> b) {
        if (a == null || b == null || a.isEmpty() || b.isEmpty()) return false;
        Set<Day> set = new HashSet<>(a.size() <= b.size() ? a : b);
        List<Day> other = (a.size() <= b.size() ? b : a);
        for (Day d : other) if (set.contains(d)) return true;
        return false;
    }





}







