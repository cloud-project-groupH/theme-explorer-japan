package com.CPGroupH.domains.place.mapper;

import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.place.entity.Visited;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitedMapper {
    default MemberVisitedResDTO toMemberVisitedResDTO(List<Visited> visited) {
        List<Long> placeIds = visited.stream()
                .map(visit -> visit.getPlace().getId())
                .collect(Collectors.toList());
        return new MemberVisitedResDTO(placeIds);
    }
}
