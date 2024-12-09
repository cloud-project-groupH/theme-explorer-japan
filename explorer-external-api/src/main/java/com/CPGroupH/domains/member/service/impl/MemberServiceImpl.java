package com.CPGroupH.domains.member.service.impl;

import com.CPGroupH.domains.member.dto.request.CategoryReqDTO;
import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.member.dto.response.MemberMapResDTO;
import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.domains.member.service.MemberService;
import com.CPGroupH.domains.place.entity.Like;
import com.CPGroupH.domains.place.entity.Visited;
import com.CPGroupH.domains.place.mapper.LikeMapper;
import com.CPGroupH.domains.place.mapper.VisitedMapper;
import com.CPGroupH.domains.place.repository.LikeRepository;
import com.CPGroupH.domains.place.repository.VisitedRepository;
import com.CPGroupH.error.code.MemberErrorCode;
import com.CPGroupH.error.exception.CustomException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final VisitedRepository visitedRepository;
    private final LikeMapper likeMapper;
    private final VisitedMapper visitedMapper;

    public Member findMemberByNickname(String nickname) {
        return memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberLikeResDTO findLikeByMemberId(Long id) {
        List<Like> likes = likeRepository.findByMemberId(id);
        return likeMapper.toMemberLikeDTO(likes);
    }

    public MemberVisitedResDTO findVisitedByMemberId(Long id){
        List<Visited> visited = visitedRepository.findByMemberId(id);
        return visitedMapper.toMemberVisitedResDTO(visited);
    }

    public MemberMapResDTO findMapByMemberId(Long id){
        return null;
    }

    public Boolean hasCompletedAllowance(String email){
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()){
            return member.get().getAllowance();
        }
        else{
            throw new CustomException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void updateAllowance(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            member.get().updateAllowance();
            memberRepository.save(member.get());
        }
        else{
            throw new CustomException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void addMemberCategory(CategoryReqDTO categoryReqDTO, Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateCategoies(categoryReqDTO.categoryIds());
        member.updateAllowance();
    }

}
