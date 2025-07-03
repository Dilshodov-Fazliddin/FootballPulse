package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.FriendUpdateDto;
import com.pulse.footballpulse.domain.response.FriendResponseDto;
import com.pulse.footballpulse.entity.FriendEntity;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.mapper.FriendMapper;
import com.pulse.footballpulse.repository.FriendRepository;
import com.pulse.footballpulse.service.FriendService;
import com.pulse.footballpulse.specification.FriendSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    private final FriendMapper friendMapper;
    private final FriendRepository friendRepository;

    @Override
    public FriendResponseDto addFriend(FriendCreateDto friendCreateDto) {
        FriendEntity friendEntity = friendMapper.toFriend(friendCreateDto);
        FriendEntity save = friendRepository.save(friendEntity);
        return friendMapper.toFriendResponse(save);
    }

    @Override
    public Page<FriendResponseDto> getFriends(Pageable pageable, UUID userId,UUID friendId,FriendStatus friendStatus) {
        Specification<FriendEntity> spec =
                FriendSpecification.hasUser(userId)
                .and(FriendSpecification.hasFriend(friendId))
                .and(FriendSpecification.hasStatus(friendStatus));


        Page<FriendEntity> all = friendRepository.findAll(spec, pageable);
        return all.map(friendMapper::toFriendResponse);
    }

    @Override
    public Page<FriendResponseDto> getByStatus(UUID userId, FriendStatus status, Pageable pageable) {
        Page<FriendEntity> friendsPage = friendRepository.findByStatus(userId, status, pageable);
        return friendsPage.map(friendMapper::toFriendResponse);
    }

    @Override
    public FriendResponseDto acceptFriend(UUID id) {
        FriendEntity friendEntity = friendRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("friendEntity not found with id: " + id));
        friendEntity.setStatus(FriendStatus.ACCEPTED);
        friendRepository.save(friendEntity);
        return friendMapper.toFriendResponse(friendEntity);
    }

    @Override
    public FriendResponseDto rejectFriend(UUID id) {
        FriendEntity friendEntity = friendRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("friendEntity not found with id: " + id));
        friendEntity.setStatus(FriendStatus.REJECTED);
        friendRepository.save(friendEntity);
        return friendMapper.toFriendResponse(friendEntity);
    }

    @Override
    public void deleteById(UUID id) {
        FriendEntity friend = friendRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Friend request not found with the id " + id));
        friendRepository.delete(friend);
    }

    @Override
    public FriendResponseDto updateFriend(UUID id, FriendUpdateDto updateDto) {
        FriendEntity friendEntity = friendRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Friend request not found with the id " + id));
        friendEntity.setStatus(updateDto.getStatus());
        friendRepository.save(friendEntity);
        return friendMapper.toFriendResponse(friendEntity);
    }


}
