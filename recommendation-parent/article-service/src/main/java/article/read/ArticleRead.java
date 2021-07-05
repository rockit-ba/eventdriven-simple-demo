package article.read;

import article.ArticlePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lucky
 */
@RestController
@RequestMapping("/read")
public class ArticleRead {

    @Autowired
    private ArticleReadService articleReadService;


    @GetMapping("/article_list/{user_id}")
    public List<ArticlePojo> articleList(@PathVariable("user_id") String userId) {
        System.out.println(userId);
        return articleReadService.listByUserId(userId);
    }
}
