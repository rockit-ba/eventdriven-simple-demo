package article.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucky
 */
@RestController
@RequestMapping("/write")
public class ArticleWrite {

    @Autowired
    private ArticleWriteService articleWriteService;

    @PostMapping("/publish_article")
    public String publishArticle(@RequestBody PublishArticleBo publishArticleBo) {
        boolean isSuccess = articleWriteService.publishArticle(publishArticleBo);
        if (!isSuccess) {
            return "发布失败，稍后重试";
        }
        return "发布成功";
    }

}
