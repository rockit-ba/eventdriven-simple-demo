package article.write;

import article.ArticleMapper;
import article.ArticlePojo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import common.event.ArticlePublishedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lucky
 */
@Service
public class ArticleWriteService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private StreamBridge streamBridge;

    private final LambdaQueryWrapper<ArticlePojo> articlePojoQW = new LambdaQueryWrapper<>();

    /**
     *  发布文章
    **/
    public boolean publishArticle(PublishArticleBo publishArticleBo) {
        ArticlePojo articlePojo = new ArticlePojo();
        articlePojo.setUserId(publishArticleBo.getUserId());
        articlePojo.setTitle(publishArticleBo.getTitle());
        articlePojo.setCreateTime(new Date());

        // 新增
        int i = articleMapper.insert(articlePojo);
        if (i != 1) {
            return false;
        }

        // 发送 文章已发布 事件消息
        ArticlePublishedEvent articlePublishedEvent = new ArticlePublishedEvent();
        articlePublishedEvent.setArticleId(articlePojo.getArticleId());
        articlePublishedEvent.setTitle(articlePojo.getTitle());
        articlePublishedEvent.setCreateTime(articlePojo.getCreateTime());
        articlePublishedEvent.setUserId(articlePojo.getUserId());
        // 如果没有事先声明 articlePublished 也会在创建的时候自动声明，且 topic 名字就为 articlePublished 没有 out-index
        // public boolean send(String bindingName, @Nullable String binderType, Object data) 此构造能够制定发送的binder
        // 如果我们添加了两个同名的bindingname，并且这两个binding分别属于rabbit和kafka，此时可以用此构造
        // 	For cases where spring.cloud.stream.source property is used or the binding was already created under different binder
        // 	the binderType 参数将不起作用
        streamBridge.send("articlePublished", articlePublishedEvent);
        return true;
    }
}
