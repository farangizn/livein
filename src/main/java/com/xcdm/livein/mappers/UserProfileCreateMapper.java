package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.UserProfileCreateDTO;
import com.xcdm.livein.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileCreateMapper {
    User toEntity(UserProfileCreateDTO userProfileCreateDTO);

    UserProfileCreateDTO toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserProfileCreateDTO userProfileCreateDTO, @MappingTarget User user);
}