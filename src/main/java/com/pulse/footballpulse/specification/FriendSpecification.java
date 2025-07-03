package com.pulse.footballpulse.specification;

import com.pulse.footballpulse.entity.FriendEntity;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class FriendSpecification {

    public static Specification<FriendEntity>hasUser(UUID userId) {
        if(userId==null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("Id"), userId);
    }


    public static Specification<FriendEntity>hasFriend(UUID friendId) {
        if(friendId==null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("friend").get("Id"), friendId);
    }
    public static Specification<FriendEntity>hasStatus(FriendStatus status) {
        if(status==null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }





}
