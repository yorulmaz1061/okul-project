package com.ozan.okulproject.entity.user;

import com.ozan.okulproject.entity.logic.Lesson;
import com.ozan.okulproject.entity.logic.Meet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    private String getFormattedTeacherNumber() {
        return String.format("TEA%03d", getId());
    }

    private Boolean isAdvisor;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Meet> meets = new ArrayList<>();


}
