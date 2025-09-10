package com.ozan.okulproject.service.helper;

import com.ozan.okulproject.config.GradingProperties;
import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import com.ozan.okulproject.enums.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeCalculator {
    private final GradingProperties props;

    public double calculateTerm(Double midterm, Double fin) {
        double m = (midterm == null) ? 0.0 : midterm;
        double f = (fin == null) ? 0.0 : fin;
        double term = (m * props.getMidtermWeight()) + (f * props.getFinalWeight());
        return Math.round(term * 100.0) / 100.0;
    }
    public Score mapToLetter(double term) {
        if (term >= 90) return Score.AA;
        if (term >= 85) return Score.BA;
        if (term >= 80) return Score.BB;
        if (term >= 75) return Score.CB;
        if (term >= 70) return Score.CC;
        if (term >= 65) return Score.DC;
        if (term >= 60) return Score.DD;
        return Score.FF;
    }
    public boolean isPassed(Integer absence, Double fin, double term) {
        int a = (absence == null) ? 0 : absence;
        double f = (fin == null) ? 0.0 : fin;
        return f >= props.getFinalMin()
                && a <= props.getAbsenceLimit()
                && term >= props.getTermMin();
    }
    public void apply(StudentLessonInfo e) {
        double term = calculateTerm(e.getMidtermExamGrade(), e.getFinalExamGrade());
        e.setTermGrade(term);
        e.setGradeLetterScore(mapToLetter(term));
        e.setIsPassed(isPassed(e.getAbsence(), e.getFinalExamGrade(), term));
    }
}