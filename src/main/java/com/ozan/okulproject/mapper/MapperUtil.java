package com.ozan.okulproject.mapper;

import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {
    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convert(Object source, T target) {
        return modelMapper.map(source, (Type) target.getClass());
    }
    public <T> T convert(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

}
