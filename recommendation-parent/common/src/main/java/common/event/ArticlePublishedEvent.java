package common.event;

import lombok.Data;

import java.util.Date;

/**
 * @author lucky
 */
@Data
public class ArticlePublishedEvent {
    private Long articleId;

    private Long userId;

    private String title;

    private Date createTime;

}
