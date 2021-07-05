package recommend;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lucky
 */
@Data
@TableName("recommend_article")
public class RecommendArticlePojo {
    @TableId(value = "user_id",type = IdType.NONE)
    private Long userId;

    private Long articleId;

}
