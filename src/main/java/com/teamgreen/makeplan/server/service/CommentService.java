package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.dto.comment.CommentResDto;
import com.teamgreen.makeplan.server.dto.comment.CreateCommentReqDto;
import com.teamgreen.makeplan.server.dto.comment.GetCommentResDto;
import com.teamgreen.makeplan.server.dto.common.PagenationDto;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.entity.document.comment.CommentDocument;
import com.teamgreen.makeplan.server.error.CommonError;
import com.teamgreen.makeplan.server.error.PostError;
import com.teamgreen.makeplan.server.error.RestApiException;
import com.teamgreen.makeplan.server.repository.CommentDocumentRepository;
import com.teamgreen.makeplan.server.repository.PostRepository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDocumentRepository commentDocumentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDocument createComment(Integer userId, CreateCommentReqDto dto) {

        if (!postRepository.existsById(dto.getPostId())) {
            throw new RestApiException(PostError.NONE_EXISTS);
        }

        User user = userRepository.findById(userId)
                                  .orElseThrow();

        CommentDocument commentDocument = CommentDocument.builder()
                                                         .postId(dto.getPostId())
                                                         .userId(userId)
                                                         .content(dto.getContent())
                                                         .createdAt(LocalDateTime.now())
                                                         .build();

        commentDocumentRepository.save(commentDocument);

        return commentDocument;
    }

    @Transactional
    public GetCommentResDto getComments(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<CommentDocument> result = commentDocumentRepository.findAllByPostIdOrderByCreatedAtDesc(postId, pageable);

        List<Integer> userIds = result.getContent()
                                      .stream()
                                      .map(CommentDocument::getUserId)
                                      .toList();

        Map<Integer, User> userMap = userRepository.findAllById(userIds)
                                                   .stream()
                                                   .collect(Collectors.toMap(User::getId, u -> u));

        List<CommentResDto> comments = result.getContent()
                                             .stream()
                                             .map(c -> {
                                                 User user = userMap.get(c.getUserId());

                                                 return CommentResDto
                                                         .builder()
                                                         .id(c.getId())
                                                         .userId(user.getId())
                                                         .username(user == null ? "탈퇴한 사용자" : user.getName())
                                                         .profileImageUrl(user == null ? User.baseProfileImageUrl : user.getProfileImageUrl())
                                                         .content(c.getContent())
                                                         .createdAt(c.getCreatedAt())
                                                         .build();
                                             })
                                             .toList();

        PagenationDto pagenationDto = PagenationDto.builder()
                                                   .page(result.getNumber())
                                                   .size(result.getSize())
                                                   .totalElements((int) result.getTotalElements())
                                                   .totalPages(result.getTotalPages())
                                                   .hasNext(result.hasNext())
                                                   .build();

        return GetCommentResDto.builder()
                               .comments(comments)
                               .pagenationDto(pagenationDto)
                               .build();
    }
}
