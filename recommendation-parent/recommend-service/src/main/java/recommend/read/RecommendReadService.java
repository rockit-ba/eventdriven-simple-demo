package recommend.read;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import recommend.ArticleMapper;
import recommend.ArticlePojo;
import recommend.RecommendArticleMapper;
import recommend.RecommendArticlePojo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lucky
 */
@Service
public class RecommendReadService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RecommendArticleMapper recommendArticleMapper;

    private final LambdaQueryWrapper<RecommendArticlePojo> recommendArticleQW =  new LambdaQueryWrapper<RecommendArticlePojo>();
    private final LambdaQueryWrapper<ArticlePojo> articleQW =  new LambdaQueryWrapper<ArticlePojo>();

    /**
     *  根据用户id 查询对应的推荐文章列表
    **/
    public List<ArticlePojo> listRecommendByUserId(String userId) {

        List<RecommendArticlePojo> recommendArticlePojos = recommendArticleMapper
                .selectList(recommendArticleQW.eq(RecommendArticlePojo::getUserId, Long.valueOf(userId)));
        // 得到文章 ids
        List<Long> articleIds = recommendArticlePojos.stream()
                .map(RecommendArticlePojo::getArticleId)
                .collect(Collectors.toList());

        return articleMapper.selectList(articleQW.in(ArticlePojo::getArticleId, articleIds));
    }
}
