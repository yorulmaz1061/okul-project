package com.ozan.okulproject;

import com.ozan.okulproject.dto.users.StudentDetailsDTO;
import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.StudentDetails;
import com.ozan.okulproject.entity.TeacherDetails;
import com.ozan.okulproject.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OkulProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkulProjectApplication.class, args);
    }

@Bean
public ModelMapper mapper(){
    ModelMapper modelMapper = new ModelMapper();

    // Explicit mapping from User → UserDTO
    modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
        mapper.map(User::getTeacherDetails, UserDTO::setTeacherDetailsDTO);
        mapper.map(User::getStudentDetails, UserDTO::setStudentDetailsDTO);
    });
    // Explicit mapping from TeacherDetails → TeacherDetailsDTO
    modelMapper.typeMap(TeacherDetails.class, TeacherDetailsDTO.class);
    modelMapper.typeMap(StudentDetails.class, StudentDetailsDTO.class);
    return modelMapper;

}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


}

