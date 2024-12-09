package com.CPGroupH.domains.member.service;

import com.CPGroupH.domains.auth.security.oauth2.entity.CustomOAuth2User;
import com.CPGroupH.domains.member.dto.request.CategoryReqDTO;
import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.member.dto.response.MemberMapResDTO;
import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.entity.Member;
import java.util.List;

public interface MemberService {
    Member findMemberByNickname(String nickname);
    MemberLikeResDTO findLikeByMemberId(Long id);
    MemberVisitedResDTO findVisitedByMemberId(Long id);
    MemberMapResDTO findMapByMemberId(Long id);
    Boolean hasCompletedAllowance(String email);
    void updateAllowance(String email);
    void addMemberCategory(CategoryReqDTO categoryReqDTO, Long id);
}
