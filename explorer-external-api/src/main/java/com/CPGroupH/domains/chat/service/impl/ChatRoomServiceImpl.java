package com.CPGroupH.domains.chat.service.impl;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.entity.ChatRoomPlace;
import com.CPGroupH.domains.chat.entity.repository.ChatRoomRepository;
import com.CPGroupH.domains.chat.service.ChatRoomService;
import com.CPGroupH.domains.place.entity.Place;
import com.CPGroupH.domains.place.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PlaceRepository placeRepository;

    public ChatRoom findChatRoomById(String id) {
        return chatRoomRepository.findChatRoomById(Long.parseLong(id)).orElseThrow(() -> new NoSuchElementException("ChatRoom이 없습니다"));
    }

    public Page<ChatRoom> getChatRoomsByParticipant(Long participantId, Pageable pageable) {

        return chatRoomRepository.findByParticipantId(participantId, pageable);
    }

    public Page<ChatRoom> getAllChatRooms(Pageable pageable) {

        return chatRoomRepository.findAll(pageable);
    }

    @Transactional
    public ChatRoom createChatRoom(String title, LocalDateTime travelDate, List<Long> placeIds) {
        List<Place> places = placeRepository.findAllById(placeIds);

        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .travelDate(travelDate)
                .build();

        for (Place place : places) {
            ChatRoomPlace chatRoomPlace = new ChatRoomPlace(chatRoom, place);
            chatRoom.getChatRoomPlaces().add(chatRoomPlace);
            place.getChatRoomPlaces().add(chatRoomPlace);
        }

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoomsByPlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with ID: " + placeId));

        return place.getChatRoomPlaces().stream()
                .map(chatRoomPlace -> chatRoomPlace.getChatRoom())
                .toList();
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new EntityNotFoundException("ChatRoom not found with id: " + chatRoomId);
        }
        chatRoomRepository.deleteById(chatRoomId);
    }
}
