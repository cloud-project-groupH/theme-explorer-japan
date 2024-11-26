package com.CPGroupH.domains.member.service;

import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.member.dto.response.MemberMapResDTO;
import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.entity.Member;

public interface MemberService {
    Member findMemberByNickname(String nickname);
    MemberLikeResDTO findLikeByMemberId(Long id);
    MemberVisitedResDTO findVisitedByMemberId(Long id);
    MemberMapResDTO findMapByMemberId(Long id);
}
