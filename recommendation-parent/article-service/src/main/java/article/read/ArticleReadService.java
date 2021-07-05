package article.read;

import article.ArticleMapper;
import article.ArticlePojo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lucky
 */
@Service
public class ArticleReadService {
    @Resource
    private ArticleMapper articleMapper;
    private final LambdaQueryWrapper<ArticlePojo> articlePojoQW = new LambdaQueryWrapper<>();

    /**
     *  根据用户id 查询用户的文章
    **/
    public List<ArticlePojo> listByUserId(String userId) {
        return articleMapper
                .selectList(articlePojoQW.eq(ArticlePojo::getUserId,Long.valueOf(userId)));
    }
}
