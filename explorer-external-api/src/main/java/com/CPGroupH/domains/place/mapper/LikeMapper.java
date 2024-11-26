package com.CPGroupH.domains.place.mapper;

import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.place.entity.Like;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    default MemberLikeResDTO toMemberLikeDTO(List<Like> likes) {
        List<Long> placeIds = likes.stream()
                .map(like -> like.getPlace().getId())
                .collect(Collectors.toList());
        return new MemberLikeResDTO(placeIds);
    }
}
