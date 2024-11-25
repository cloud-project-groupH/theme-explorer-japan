package com.CPGroupH.domains.member.service;

import com.CPGroupH.domains.member.entity.Member;

public interface MemberService {
    Member findMemberByNickname(String nickname);
}
