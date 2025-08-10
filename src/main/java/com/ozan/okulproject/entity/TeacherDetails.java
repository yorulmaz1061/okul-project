package com.ozan.okulproject.entity;

import com.ozan.okulproject.entity.logic.Lesson;
import com.ozan.okulproject.entity.logic.Meet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDetails {
    private Boolean isAdvisor;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Meet> meetsForTeacher = new ArrayList<>();
}
