# eventdriven-simple-demo
使用springcloud-stream实现事件驱动简单示例

主要为三个服务：

article: 负责获取用户编写的文章。

relation：关系服务，某个人关注的作者。

recommend：根据用户id获取推荐给该用户的文章。

详情可看代码注释。

```mysql
# 文章服务数据库
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

# 推荐服务数据库
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recommend_article` (
  `user_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 关系服务数据库
CREATE TABLE `attentions` (
  `user_id` int(11) NOT NULL,
  `attention_user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`attention_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT '',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
```

