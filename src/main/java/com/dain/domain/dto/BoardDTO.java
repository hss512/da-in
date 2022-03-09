package com.dain.domain.dto;

import com.dain.domain.entity.Board;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long boardId;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private String ltAge;
    private String rtAge;
    private String local;
    private String gender;


    @Getter
    @NoArgsConstructor
    public static class CreateBoard{

        private String nickname;

        private String local;

        private String ltAge;

        private String rtAge;

        private String gender;

        private String title;

        private String content;

        @Builder
        public CreateBoard(String nickname, String local, String ltAge, String rtAge, String gender, String title, String content){
            this.nickname = nickname;
            this.local = local;
            this.ltAge = ltAge;
            this.rtAge = rtAge;
            this.gender = gender;
            this.title = title;
            this.content = content;
        }

        public Board toBoard(){
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .local(this.local)
                    .ltAge(Integer.parseInt(this.ltAge))
                    .rtAge(Integer.parseInt(this.rtAge))
                    .gender(this.gender)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseBoardDTO {
        private Long id;

        @Builder
        public ResponseBoardDTO(Long id){
            this.id = id;
        }
    }
}
