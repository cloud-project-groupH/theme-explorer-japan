package com.CPGroupH.domains.place.mapper;

import com.CPGroupH.domains.place.dto.response.PersonalPlaceResDTO;
import com.CPGroupH.domains.place.dto.response.PopularPlaceResDTO;
import com.CPGroupH.domains.place.dto.response.SearchPlaceResDTO;
import com.CPGroupH.domains.place.entity.Place;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(target = "imageUrl", source = "imageKey")
    List<PopularPlaceResDTO> toPopularPlaceResDTO(List<Place> places);

    @Mapping(target = "imageUrl", source = "imageKey")
    List<PersonalPlaceResDTO> toPersonalPlaceResDTO(List<Place> places);

    @Mapping(target = "imageUrl", source = "imageKey")
    List<SearchPlaceResDTO> toSearchPlaceResDTO(List<Place> places);
}
