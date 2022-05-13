//package com.pablito.shop.mapper.impl;
//
//import com.pablito.shop.domain.dao.User;
//import com.pablito.shop.domain.dto.UserDto;
//import com.pablito.shop.mapper.UserMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMapperImpl implements UserMapper {
//
//    @Override
//    public User toDao(UserDto userDto) {
//        return User.builder()
//                .id(userDto.getId())
//                .email(userDto.getEmail())
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .username(userDto.getUsername())
//                .password(userDto.getPassword())
//                .build();
//    }
//
//    @Override
//    public UserDto toDto(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .username(user.getUsername())
//                .build();
//    }
//}
