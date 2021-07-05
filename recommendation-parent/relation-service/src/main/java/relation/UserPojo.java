package relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lucky
 */
@Data
@TableName("user")
public class UserPojo {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    private String username;
}
