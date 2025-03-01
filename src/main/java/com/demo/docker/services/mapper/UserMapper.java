package com.demo.docker.services.mapper;

import com.demo.docker.dto.UserDTO;
import com.demo.docker.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a UserDTO to a UserEntity
     *
     * @param dto the UserDTO to convert
     * @return the converted UserEntity
     */
    UserEntity toEntity(UserDTO dto);

    /**
     * Convert a UserEntity to a UserDTO
     *
     * @param entity the UserEntity to convert
     * @return the converted UserDTO
     */
    UserDTO toDTO(UserEntity entity);

}
