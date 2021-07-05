package recommend.write;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import common.event.RecommendRelationProcessedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import recommend.ArticleMapper;
import recommend.ArticlePojo;
import recommend.RecommendArticleMapper;
import recommend.RecommendArticlePojo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lucky
 */
@Service
public class RecommendWriteService extends ServiceImpl<RecommendArticleMapper,RecommendArticlePojo> {

    @Resource
    private ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateRecommend(RecommendRelationProcessedEvent recommendRelationProcessedEvent) {
        ArticlePojo articlePojo = new ArticlePojo();
        articlePojo.setArticleId(recommendRelationProcessedEvent.getArticleId());
        articlePojo.setTitle(recommendRelationProcessedEvent.getTitle());
        articlePojo.setCreateTime(recommendRelationProcessedEvent.getArticleCreateTime());

        articleMapper.insert(articlePojo);

        List<Long> userIds = recommendRelationProcessedEvent.getUserIds();

        ArrayList<RecommendArticlePojo> recommendArticlePojos = new ArrayList<>();

        userIds.forEach(userId -> {
            RecommendArticlePojo recommendArticlePojo = new RecommendArticlePojo();
            recommendArticlePojo.setUserId(userId);
            recommendArticlePojo.setArticleId(recommendRelationProcessedEvent.getArticleId());
            recommendArticlePojos.add(recommendArticlePojo);
        });
        saveBatch(recommendArticlePojos);

    }
}
