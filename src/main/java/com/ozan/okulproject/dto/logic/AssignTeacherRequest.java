package com.ozan.okulproject.dto.logic;

import javax.validation.constraints.NotNull;

public record AssignTeacherRequest(@NotNull Long teacherId) {

}
