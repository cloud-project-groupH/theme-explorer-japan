package com.CPGroupH.domains.place.service;

import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.domains.place.entity.Like;
import com.CPGroupH.domains.place.entity.Place;
import com.CPGroupH.domains.place.entity.Visited;
import com.CPGroupH.domains.place.repository.LikeRepository;
import com.CPGroupH.domains.place.repository.PlaceRepository;
import com.CPGroupH.domains.place.repository.VisitedRepository;
import com.CPGroupH.error.code.MemberErrorCode;
import com.CPGroupH.error.code.PlaceErrorCode;
import com.CPGroupH.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final VisitedRepository visitedRepository;
    /*
    좋아요 기능
     */
    @Transactional
    public void like(Long memberId, Long placeId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(PlaceErrorCode.PLACE_NOT_FOUND));

        likeRepository.save(new Like(member, place));
        place.increaseLikes(place);
    }

    /*
    갔다 왔어요 기능
     */
    @Transactional
    public void visited(Long memberId, Long placeId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(PlaceErrorCode.PLACE_NOT_FOUND));

        visitedRepository.save(new Visited(member, place));
        place.increaseVisited(place);
    }
}
