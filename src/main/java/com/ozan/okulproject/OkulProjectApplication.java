package com.ozan.okulproject;

import com.ozan.okulproject.dto.users.StudentDetailsDTO;
import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.StudentDetails;
import com.ozan.okulproject.entity.TeacherDetails;
import com.ozan.okulproject.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OkulProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkulProjectApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        // User → UserDTO mapping
        TypeMap<User, UserDTO> userMap = modelMapper.typeMap(User.class, UserDTO.class);
        userMap.addMappings(mapper -> {
            // TeacherDetails ve StudentDetails özel map
            mapper.map(User::getTeacherDetails, UserDTO::setTeacherDetailsDTO);
            mapper.map(User::getStudentDetails, UserDTO::setStudentDetailsDTO);
        });

        // Eğer teacherDetailsDTO veya studentDetailsDTO null ise map'lememesi için skip
        userMap.addMappings(mapper -> {
            mapper.skip(UserDTO::setTeacherDetailsDTO);
            mapper.skip(UserDTO::setStudentDetailsDTO);
        });

        // TeacherDetails → TeacherDetailsDTO
        modelMapper.typeMap(TeacherDetails.class, TeacherDetailsDTO.class);

        // StudentDetails → StudentDetailsDTO
        modelMapper.typeMap(StudentDetails.class, StudentDetailsDTO.class);

        return modelMapper;
    }

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


}

