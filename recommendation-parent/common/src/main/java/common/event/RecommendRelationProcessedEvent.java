package common.event;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lucky
 */
@Data
public class RecommendRelationProcessedEvent {
    private Long articleId;
    private String title;
    private Date articleCreateTime;
    private List<Long> userIds;
}
