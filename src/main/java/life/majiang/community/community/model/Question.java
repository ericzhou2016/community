package life.majiang.community.community.model;

import lombok.Data;

@Data
public class Question {
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
}
