package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.UserProfileReadDTO;
import com.xcdm.livein.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserProfileReadDTO userProfileReadDTO);

    UserProfileReadDTO toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserProfileReadDTO userProfileReadDTO, @MappingTarget User user);
}