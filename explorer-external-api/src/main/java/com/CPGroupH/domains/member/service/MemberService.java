package com.CPGroupH.domains.member.service;

import com.CPGroupH.domains.member.dto.request.CategoryReqDTO;
import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.member.dto.response.MemberMapResDTO;
import com.CPGroupH.domains.member.dto.response.MemberResDTO;
import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.entity.Member;

public interface MemberService {
    Member findMemberByNickname(String nickname);
    MemberLikeResDTO findLikeByMemberId(Long id);
    MemberVisitedResDTO findVisitedByMemberId(Long id);
    MemberMapResDTO findMapByMemberId(Long id);
    Boolean hasCompletedAllowance(String email);
    void updateAllowance(String email);
    void addMemberPreference(CategoryReqDTO categoryReqDTO, Long id);
    MemberResDTO findMemberById(Long id);
}
