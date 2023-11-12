package com.healthcare.communityservice.controller;

import com.healthcare.communityservice.dto.*;
import com.healthcare.communityservice.exception.GroupNotMatchException;
import com.healthcare.communityservice.exception.NameAlreadyExistsException;
import com.healthcare.communityservice.exception.NullValueException;
import com.healthcare.communityservice.exception.ValueNotFoundException;
import com.healthcare.communityservice.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping("/groups/create")
    public String createGroup(@RequestBody GroupDto groupDto) throws NullValueException, NameAlreadyExistsException {
        if (groupDto.getGroupName() == null){
            throw new NullValueException("Group name should not be empty.");
        }
        communityService.createGroup(groupDto);
        return "Group has been created";
    }

    @PostMapping("/groups/join/{groupId}")
    public String joinGroup(@PathVariable Long groupId)
            throws ValueNotFoundException, NameAlreadyExistsException {
        communityService.joinGroup(groupId);
        return "You can now post on group " + groupId;
    }

    @PostMapping("/posts/{groupId}")
    public PostDto postOnGroup(@PathVariable Long groupId,
                               @RequestBody PostDto postDto) throws ValueNotFoundException, GroupNotMatchException {
        return communityService.postOnGroup(groupId, postDto);
    }

    @PostMapping("/like/{postId}")
    public LikeDto likeOnPost(@PathVariable Long postId) throws ValueNotFoundException, GroupNotMatchException, NameAlreadyExistsException {
        return communityService.likeOnPost(postId);
    }

    @PostMapping("/comment/{postId}")
    public CommentDto commentOnPost(@PathVariable Long postId,
                                    @RequestBody CommentDto commentDto) throws ValueNotFoundException, GroupNotMatchException {
        return communityService.commentOnPost(postId, commentDto);
    }

    @PostMapping("/follower/{followerUserId}")
    public FollowerDto followIndividualUser(@PathVariable Long followerUserId) throws NameAlreadyExistsException {
        return communityService.followIndividualUser(followerUserId);
    }

    @DeleteMapping("/unfollow/{followerUserId}")
    public String unfollowIndividualUser(@PathVariable Long followerUserId) throws ValueNotFoundException {
        communityService.unfollowIndividualUser(followerUserId);
        return "You unfollowed user " + followerUserId;
    }

    @GetMapping("/get-follower")
    public String countFollower(){
        Long count = communityService.countIndividualFollower();
        return "You are followed by " + count + " user.";
    }

    @DeleteMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable Long postId) throws ValueNotFoundException, NullValueException {
        communityService.deletePost(postId);
        return "Successfully deleted post " + postId;
    }

    @GetMapping("/post-info/{postId}")
    public IndividualPostInfoDto postInfo(@PathVariable Long postId)
            throws ValueNotFoundException, GroupNotMatchException {
        return communityService.getInfoOfIndividualPost(postId);
    }

    @GetMapping("/group-posts/{groupId}")
    public List<IndividualPostInfoDto> groupPosts(@PathVariable Long groupId)
            throws ValueNotFoundException, GroupNotMatchException {
        return communityService.getGroupPosts(groupId);
    }
}
