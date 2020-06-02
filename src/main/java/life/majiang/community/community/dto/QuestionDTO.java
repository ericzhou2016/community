package life.majiang.community.community.dto;


import life.majiang.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;

    private long gmtCreater;
    private long gmtModified;
    private Integer creator;

    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;


    private User user;
}
