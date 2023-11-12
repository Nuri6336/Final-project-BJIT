package com.healthcare.communityservice.dto;

import com.healthcare.communityservice.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualPostInfoDto {

    private Long postId;
    private String postDetails;
    private Long countLikes;
    private List<Comment> comments;
}
