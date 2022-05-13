package com.pablito.shop.mapper;

import com.pablito.shop.domain.dao.User;
import com.pablito.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDao(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);
}
