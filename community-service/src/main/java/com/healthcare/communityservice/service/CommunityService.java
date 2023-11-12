package com.healthcare.communityservice.service;


import com.healthcare.communityservice.dto.*;
import com.healthcare.communityservice.exception.GroupNotMatchException;
import com.healthcare.communityservice.exception.NameAlreadyExistsException;
import com.healthcare.communityservice.exception.NullValueException;
import com.healthcare.communityservice.exception.ValueNotFoundException;

import java.util.List;

public interface CommunityService {

    void createGroup(GroupDto groupDto) throws NameAlreadyExistsException;
    void joinGroup(Long groupId) throws ValueNotFoundException, NameAlreadyExistsException;

    PostDto postOnGroup(Long groupId, PostDto postDto) throws ValueNotFoundException, GroupNotMatchException;
    LikeDto likeOnPost(Long postId) throws ValueNotFoundException, GroupNotMatchException, NameAlreadyExistsException;
    CommentDto commentOnPost(Long postId, CommentDto commentDto) throws ValueNotFoundException, GroupNotMatchException;

    FollowerDto followIndividualUser(Long followerUserId) throws NameAlreadyExistsException;
    void unfollowIndividualUser(Long followerUserId) throws ValueNotFoundException;
    Long countIndividualFollower();

    IndividualPostInfoDto getInfoOfIndividualPost(Long postId) throws ValueNotFoundException, GroupNotMatchException;
    List<IndividualPostInfoDto> getGroupPosts(Long groupId) throws ValueNotFoundException, GroupNotMatchException;
    void deletePost(Long postId) throws ValueNotFoundException, NullValueException;
}
