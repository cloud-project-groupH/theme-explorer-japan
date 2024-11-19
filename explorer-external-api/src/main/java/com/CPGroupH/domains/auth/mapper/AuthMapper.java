package com.CPGroupH.domains.auth.mapper;

import com.CPGroupH.domains.auth.dto.response.AllowanceResponse;
import com.CPGroupH.domains.auth.dto.response.RefreshResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AllowanceResponse toAllowanceResponse(String accessToken, String refreshToken);

    RefreshResponse toRefreshResponse(String accessToken);
}
