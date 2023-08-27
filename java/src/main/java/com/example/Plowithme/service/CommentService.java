package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.community.CommentDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.CommentException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.CommentRepository;
import com.example.Plowithme.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    //모든 댓글 내용 출력
//    public List<Comment> getAllComments() {
//        commentRepository.findAll();
//    }

    public void saveComment(User currentUser, CommentDto commentDto) {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        //부모 엔티티(boardEntity) 조회
        Optional<BoardEntity> optionalBoardEntity= boardRepository.findById(commentDto.getPostId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity=optionalBoardEntity.get();
            Comment comment=Comment.toComment(commentDto);
            commentRepository.save(comment);
        } else {
         throw new CommentException("게시글을 찾을 수 없습니다.");
        }

      //  comment.setWriter(currentUser.getNickname());

    }

    //Id를 통해 comment 찾기
    public CommentDto findById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment= optionalComment.get();
            CommentDto commentDto = CommentDto.toCommentDto(comment);
            return commentDto;
        } else {
            return null;
        }


    }

    //댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


    //댓글 목록 조회
    public List<CommentDto> findAllCommentOfPost(Long postId) {
        BoardEntity boardEntity=boardRepository.findById(postId).get();
        List<Comment> commentList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);


        //entitylist>dto list
        List<CommentDto> commentDtoList=new ArrayList<>();
        for (Comment comment : commentList) {
            CommentDto commentDto=CommentDto.toCommentDto(comment);
            commentDtoList.add(commentDto);
        } return commentDtoList;
    }

//    @Transactional()
//    public List<CommentDto> findAllCommentOfPost(Long postId) {
//        //service가 데이터를 가져올 때 repository에게 시킨다
//
//        List<Comment> comments = commentRepository.findAllByPostId(postId);
//        List<CommentDto> commentDtos = new ArrayList<>();
//
//        comments.forEach(s -> commentDtos.add(CommentDto.toCommentDto(s)));
//        return commentDtos;
//    }
}