package recommend.event;

import common.event.RecommendRelationProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import recommend.write.RecommendWriteService;

import java.util.function.Consumer;

/**
 * 文章推荐关系已处理 事件处理
 * @author lucky
 */
@Configuration
public class RecommendRelationProcessedHandle {
    @Autowired
    private RecommendWriteService recommendWriteService;

    @Bean
    public Consumer<Message<RecommendRelationProcessedEvent>> recommendAdded() {
        return message -> {
            RecommendRelationProcessedEvent recommendRelationProcessedEvent = message.getPayload();
            System.out.println("推荐文章新增事件："+recommendRelationProcessedEvent.getTitle());
            // 发布的 文章数据 本地存储
            recommendWriteService.updateRecommend(recommendRelationProcessedEvent);

        };
    }

}
