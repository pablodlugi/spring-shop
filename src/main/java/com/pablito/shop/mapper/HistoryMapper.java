package com.pablito.shop.mapper;

import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.domain.dao.User;
import com.pablito.shop.domain.dto.ProductDto;
import com.pablito.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")//pozwala na wygenerowanie implementacji tej klasy w stylu springowym
public interface HistoryMapper {
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.username", target = "username")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto mapToUserDto(Revision<Integer, User> revision);

    @Mapping(source = "entity.id", target = "id") //customowy mappingdla zmiennej o nazwie id w productDTO
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.code", target = "code")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    ProductDto mapToProductDto(Revision<Integer, Product> revision);
}
