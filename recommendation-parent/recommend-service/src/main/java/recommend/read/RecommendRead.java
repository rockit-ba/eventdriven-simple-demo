package recommend.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recommend.ArticlePojo;

import java.util.List;

/**
 * @author lucky
 */
@RestController
@RequestMapping("/read")
public class RecommendRead {
    @Autowired
    private RecommendReadService recommendReadService;

    @GetMapping("/recommend/{user_id}")
    public List<ArticlePojo> list(@PathVariable("user_id") String userId) {
        return recommendReadService.listRecommendByUserId(userId);
    }

}
