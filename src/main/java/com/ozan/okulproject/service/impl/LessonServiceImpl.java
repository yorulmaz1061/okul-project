package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.logic.EducationTermDTO;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.dto.logic.StudentLessonInfoDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.entity.logic.EducationTerm;
import com.ozan.okulproject.entity.logic.Lesson;
import com.ozan.okulproject.entity.logic.LessonSchedule;
import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import com.ozan.okulproject.enums.Day;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.*;
import com.ozan.okulproject.service.LessonService;
import com.ozan.okulproject.service.helper.GradeCalculator;
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
    private final StudentLessonInfoRepository studentLessonInfoRepository;
    private final GradeCalculator gradeCalculator;


    public LessonServiceImpl(MapperUtil mapperUtil, LessonRepository lessonRepository, EducationTermRepository educationTermRepository, UserRepository userRepository, LessonScheduleRepository lessonScheduleRepository, @Lazy LessonService lessonService, StudentLessonInfoRepository studentLessonInfoRepository, GradeCalculator gradeCalculator) {
        this.mapperUtil = mapperUtil;
        this.lessonRepository = lessonRepository;
        this.educationTermRepository = educationTermRepository;
        this.userRepository = userRepository;
        this.lessonScheduleRepository = lessonScheduleRepository;
        this.lessonService = lessonService;
        this.studentLessonInfoRepository = studentLessonInfoRepository;
        this.gradeCalculator = gradeCalculator;
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
            dto.setTermLabel(term.getTermLabel());
            EducationTermDTO etDto = new EducationTermDTO();
            etDto.setId(term.getId());
            etDto.setStartDate(term.getStartDate());
            etDto.setEndDate(term.getEndDate());
            etDto.setLastRegistrationDate(term.getLastRegistrationDate());
            etDto.setTermLabel(term.getTermLabel());
            dto.setEducationTermId(etDto);
        }
        if (lesson.getTeacher() != null) {
            User t = lesson.getTeacher();
            UserDTO brief = new UserDTO();
            brief.setId(t.getId());
            brief.setFirstName(t.getFirstName());
            brief.setLastName(t.getLastName());
            dto.setTeacherId(brief);
            dto.setIsTeacherAssigned(true);
            dto.setAssignedTeacherInformation(t.getId() + " - " + t.getFirstName() + " - " + t.getLastName());
        } else {
            dto.setTeacherId(null);
            dto.setIsTeacherAssigned(false);
            dto.setAssignedTeacherInformation(null);
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
                UserDTO brief = new UserDTO();
                brief.setId(teacher.getId());
                brief.setFirstName(teacher.getFirstName());
                brief.setLastName(teacher.getLastName());
                dto.setTeacherId(brief);
                dto.setIsTeacherAssigned(true);
                dto.setAssignedTeacherInformation(teacher.getId() + " - " + teacher.getFirstName() + " - " + teacher.getLastName());
            } else {
                dto.setTeacherId(null);
                dto.setIsTeacherAssigned(false);
                dto.setAssignedTeacherInformation(null);
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
        LessonScheduleDTO dto = new LessonScheduleDTO();
        dto.setDayList(ls.getDayList());
        dto.setStartTime(ls.getStartTime());
        dto.setEndTime(ls.getEndTime());
        Lesson lesson = ls.getLesson();
        if (lesson != null) {
            LessonDTO lDto = new LessonDTO();
            lDto.setId(lesson.getId());
            EducationTerm term = lesson.getEducationTerm();
            if (term != null) {
                lDto.setTermLabel(term.getTermLabel());
                EducationTermDTO etDto = new EducationTermDTO();
                etDto.setId(term.getId());
                 etDto.setTermLabel(term.getTermLabel());
                lDto.setEducationTermId(etDto);
            }
            dto.setLesson(lDto);
        }
        return dto;
    }

    @Override
    @Transactional
    public LessonDTO assignTeacherToLesson(Long lessonId, Long teacherId) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, lessonId);
        if (lesson == null) {
            throw new OkulProjectException("Lesson not found with id: " + lessonId);
        }
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new OkulProjectException("Teacher not found with id: " + teacherId));
        if (teacher.getRole() != Role.TEACHER)
            throw new OkulProjectException("Only TEACHER can be assigned.");
        if (!teacher.isEnabled())
            throw new OkulProjectException("Teacher account is disabled.");
        if (lesson.getTeacher() != null && lesson.getTeacher().getId().equals(teacherId)) {
            throw new OkulProjectException("Teacher is already assigned to this lesson");
        }
        lesson.setTeacher(teacher);
        lesson.setIsTeacherAssigned(true);
        Lesson saved = lessonRepository.save(lesson);
        LessonDTO dto = mapperUtil.convert(saved, LessonDTO.class);
        if (saved.getTeacher() != null) {
            User t = saved.getTeacher();
            UserDTO brief = new UserDTO();
            brief.setId(t.getId());
            brief.setFirstName(t.getFirstName());
            brief.setLastName(t.getLastName());
            dto.setTeacherId(brief);
            dto.setIsTeacherAssigned(true);
            dto.setAssignedTeacherInformation(
                    t.getId() + " - " + t.getFirstName() + " - " + t.getLastName()
            );
        } else {
            dto.setIsTeacherAssigned(false);
            dto.setTeacherId(null);
            dto.setAssignedTeacherInformation(null);
        }
        if (saved.getEducationTerm() != null) {
            dto.setTermLabel(saved.getEducationTerm().getTermLabel());
            EducationTermDTO etDto = new EducationTermDTO();
            etDto.setId(saved.getEducationTerm().getId());
            etDto.setTermLabel(saved.getEducationTerm().getTermLabel());
            dto.setEducationTermId(etDto);
        }
        dto.setTotalStudentsCounts(String.valueOf(
                saved.getStudentLessonInfo() == null ? 0 : saved.getStudentLessonInfo().size()
        ));
        return dto;
    }

    @Override
    @Transactional
    public LessonScheduleDTO deleteLessonScheduleByLessonId(Long lessonId) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, lessonId);
        if (lesson == null) {
            throw new OkulProjectException("Lesson not found with id: " + lessonId);
        }
        List<LessonSchedule> schedules =
                lessonScheduleRepository.findAllByLesson_IdAndIsDeletedFalse(lessonId);
        if (schedules.isEmpty()) {
            throw new OkulProjectException("No lesson schedule found for lesson id: " + lessonId);
        }
        schedules.forEach(s -> s.setIsDeleted(true));
        lessonScheduleRepository.saveAll(schedules);
        LessonSchedule first = schedules.get(0);
        LessonScheduleDTO dto = new LessonScheduleDTO();
        dto.setDayList(first.getDayList());
        dto.setStartTime(first.getStartTime());
        dto.setEndTime(first.getEndTime());
        LessonDTO lDto = new LessonDTO();
        lDto.setId(lesson.getId());
        if (lesson.getEducationTerm() != null) {
            EducationTerm term = lesson.getEducationTerm();
            lDto.setTermLabel(term.getTermLabel());
            EducationTermDTO et = new EducationTermDTO();
            et.setId(term.getId());
            lDto.setEducationTermId(et);
        }
        dto.setLesson(lDto);
        return dto;
    }
    @Override
    @Transactional
    public StudentLessonInfoDTO enrollStudent(Long lessonId, Long studentId) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, lessonId);
        if (lesson == null) throw new OkulProjectException("Lesson not found with id: " + lessonId);
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new OkulProjectException("Student not found with id: " + studentId));
        if (student.getRole() != Role.STUDENT)
            throw new OkulProjectException("Only students can enroll to this lesson.");
        if (!student.isEnabled())
            throw new OkulProjectException("Student is not enabled.");
        boolean exists = studentLessonInfoRepository
                .existsByLesson_IdAndStudent_IdAndIsDeletedFalse(lessonId, studentId);
        if (exists) throw new OkulProjectException("Student already enrolled to this lesson.");
        List<LessonSchedule> targetSchedules =
                lessonScheduleRepository.findAllByLesson_IdAndIsDeletedFalse(lessonId);
        if (!targetSchedules.isEmpty()) {
            List<LessonSchedule> otherSchedules =
                    studentLessonInfoRepository.findAllByStudent_IdAndIsDeletedFalse(studentId).stream()
                            .map(e -> e.getLesson().getId())
                            .filter(id -> !Objects.equals(id, lessonId))
                            .distinct()
                            .flatMap(id -> lessonScheduleRepository
                                    .findAllByLesson_IdAndIsDeletedFalse(id).stream())
                            .toList();
            boolean conflict = targetSchedules.stream().anyMatch(tgt ->
                    otherSchedules.stream().anyMatch(oth ->
                            hasDayIntersection(tgt.getDayList(), oth.getDayList()) &&
                                    overlaps(tgt.getStartTime(), tgt.getEndTime(), oth.getStartTime(), oth.getEndTime())
                    )
            );
            if (conflict) {
                throw new OkulProjectException(
                        "Schedule conflict: Student has another enrolled lesson that overlaps with this lesson's slot(s).");
            }
        }
        StudentLessonInfo sli = new StudentLessonInfo();
        sli.setLesson(lessonRepository.getReferenceById(lessonId));
        sli.setStudent(userRepository.getReferenceById(studentId));
        studentLessonInfoRepository.save(sli);
        StudentLessonInfoDTO dto = new StudentLessonInfoDTO();
        UserDTO s = new UserDTO();
        s.setId(student.getId());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        dto.setStudent(s);
        LessonDTO l = new LessonDTO();
        l.setId(lesson.getId());
        if (lesson.getEducationTerm() != null) {
            EducationTermDTO et = new EducationTermDTO();
            et.setId(lesson.getEducationTerm().getId());
            et.setTermLabel(lesson.getEducationTerm().getTermLabel());
            l.setEducationTermId(et);
            l.setTermLabel(et.getTermLabel());
        }
        long cnt = studentLessonInfoRepository.countByLesson_IdAndIsDeletedFalse(lessonId);
        l.setTotalStudentsCounts(String.valueOf(cnt));

        dto.setLesson(l);
        return dto;
    }

    @Override
    @Transactional
    public void unenrollStudentFromLesson(Long lessonId, Long studentId) {
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, lessonId);
        if (lesson == null) {
            throw new OkulProjectException("Lesson not found with id: " + lessonId);
        }
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new OkulProjectException("Student not found with id: " + studentId));
        if (student.getRole() != Role.STUDENT) {
            throw new OkulProjectException("Only STUDENT can be unenrolled.");
        }
        StudentLessonInfo sli = studentLessonInfoRepository
                .findByLesson_IdAndStudent_IdAndIsDeletedFalse(lessonId, studentId)
                .orElseThrow(() -> new OkulProjectException(
                        "Enrollment not found for student " + studentId + " in lesson " + lessonId));

        sli.setIsDeleted(true);
        studentLessonInfoRepository.save(sli);
        long cnt = studentLessonInfoRepository.countByLesson_IdAndIsDeletedFalse(lessonId);
        lesson.setStudentLessonInfo(
                lesson.getStudentLessonInfo().stream()
                        .filter(info -> !info.getIsDeleted())
                        .collect(Collectors.toList())
        );
        lessonRepository.save(lesson);
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

    @Override
    public StudentLessonInfoDTO gradeStudent(Long lessonId, Long studentId, StudentLessonInfoDTO dto) {
        User student = userRepository.findByIdAndIsDeleted(studentId, false);
        if (student == null) throw new OkulProjectException("Student not found with id: " + studentId);
        Lesson lesson = lessonRepository.findByIsDeletedAndId(false, lessonId);
        if (lesson == null) throw new OkulProjectException("Lesson not found with id: " + lessonId);
        StudentLessonInfo sli = studentLessonInfoRepository
                .findByLesson_IdAndStudent_IdAndIsDeletedFalse(lessonId, studentId)
                .orElseThrow(() -> new OkulProjectException("Enrollment not found for this lesson/student."));
        sli.setAbsence(dto.getAbsence());
        sli.setMidtermExamGrade(dto.getMidtermExamGrade());
        sli.setFinalExamGrade(dto.getFinalExamGrade());
        sli.setInfoNote(dto.getInfoNote());
        gradeCalculator.apply(sli);
        studentLessonInfoRepository.save(sli);
        StudentLessonInfoDTO out = new StudentLessonInfoDTO();
        out.setAbsence(sli.getAbsence());
        out.setMidtermExamGrade(sli.getMidtermExamGrade());
        out.setFinalExamGrade(sli.getFinalExamGrade());
        out.setTermGrade(sli.getTermGrade());
        out.setInfoNote(sli.getInfoNote());
        out.setGradeLetterScore(sli.getGradeLetterScore());
        out.setIsPassed(sli.getIsPassed());
        UserDTO s = new UserDTO();
        s.setId(student.getId());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        out.setStudent(s);
        LessonDTO l = new LessonDTO();
        l.setId(lesson.getId());
        if (lesson.getEducationTerm() != null) {
            EducationTermDTO et = new EducationTermDTO();
            et.setId(lesson.getEducationTerm().getId());
            et.setTermLabel(lesson.getEducationTerm().getTermLabel());
            l.setEducationTermId(et);
            l.setTermLabel(et.getTermLabel());
        }
        out.setLesson(l);
        return out;
    }

}







