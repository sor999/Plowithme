package com.example.Plowithme.entity;

import com.example.Plowithme.dto.request.community.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//db 테이블 역할을 하는 클래스
@Entity
@Table(name = "board")
@Getter
@Setter
public class BoardEntity extends BaseEntity {

    /*
    @NotNull : Null 값 체크
    @NotEmpty : Null, "" 체크
    @NotBlank : Null, "", 공백을 포함한 빈값 체크
     */

    @Id //pk 컬럼 지정. 필수
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 100) //크기 50, not null 지정
   // @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @Column(length = 5000) //크기 5000, null 가능
    //@NotEmpty(message = "내용을 입력해주세요.")
    private String contents;

    @Column(name="board_category")
    //@NotBlank(message = "카테고리를 선택해주세요.")
    private int category;

    @Column
    private long postHits;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList=new ArrayList<>();

    /*
    @Column
    private String postWriter;

    시간에 관한 것은 bastEntity에 별도로 담음. baseEntity에 담았는데 주석처리한 것들을 실행시키면 에러남
    @Column
    private String createDate;

    @Column
    private String updateDate;

    @Column
    private String postImage;

    @Column
    private String imagePath;

*/

    //엔티티 객체를 객체로 만들어서 호출하는 게 아닌 그냥 클래스 메소드로 정의
    public static BoardEntity toSaveEntity(BoardDto boardDto) {
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContents(boardDto.getContents());
        boardEntity.setPostHits(boardDto.getPostHits());
        boardEntity.setCategory(boardDto.getCategory());
        //boardEntity.setPostImage(boardDto.getPostImage());
        //boardEntity.setImagePath(boardDto.getImagePath());
        //dto에 담긴 것을 entity로 넘김(변환)
        //에러가 나거나 값이 생각한 값이 아니면 이 부분에서 문제가 있을 가능성 큼
        return boardEntity;
    }


}
