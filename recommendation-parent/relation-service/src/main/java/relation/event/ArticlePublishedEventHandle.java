package relation.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import common.event.ArticlePublishedEvent;
import common.event.RecommendRelationProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import relation.AttentionsMapper;
import relation.AttentionsPojo;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文章发布事件处理
 * @author lucky
 */
@Configuration
public class ArticlePublishedEventHandle {
    @Resource
    private AttentionsMapper attentionsMapper;
    private final LambdaQueryWrapper<AttentionsPojo> attentionsPojoQW = new LambdaQueryWrapper<AttentionsPojo>();

    /**
     *  文章发布 消息 function
     *  返回 推荐关系已处理事件
     *  可直接写为 Function<ArticlePublishedEvent, RecommendRelationProcessedEvent>
     *      通过接受message 类型我们能够获取更多信息，比方消息头,有时候我们可以根据消息头做不同的业务处理
     *      消息头可获取的信息：
     *      id：消息发送时系统自动生成的UUID
     *      timestamp：消息的时间戳
     *      contentType
     *      replyChannel
     *      errorChannel
    **/
    @Bean
    public Function<Message<ArticlePublishedEvent>, RecommendRelationProcessedEvent> recommendRelationProcessed() {
        return message -> {
            // 得到文章发布者 id
            ArticlePublishedEvent articlePublishedEvent = message.getPayload();

            Long articleUserId = articlePublishedEvent.getUserId();

            // 根据文章发布者 查询被关注的人userids 这些人的推荐文章需要更新
            List<Long> userIds = attentionsMapper
                    .selectList(attentionsPojoQW.eq(AttentionsPojo::getAttentionUserId, articleUserId))
                    .stream()
                    .map(AttentionsPojo::getUserId)
                    .collect(Collectors.toList());


            RecommendRelationProcessedEvent recommendRelationProcessedEvent = new RecommendRelationProcessedEvent();
            recommendRelationProcessedEvent.setArticleId(articlePublishedEvent.getArticleId());
            recommendRelationProcessedEvent.setTitle(articlePublishedEvent.getTitle());
            recommendRelationProcessedEvent.setArticleCreateTime(articlePublishedEvent.getCreateTime());
            // 设置相关用户id
            recommendRelationProcessedEvent.setUserIds(userIds);
            return recommendRelationProcessedEvent;
        };
    }

}
