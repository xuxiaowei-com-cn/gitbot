package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Getter
@Setter
@TableName("gl_namespace")
public class GlNamespace implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String path;

    private String kind;

    private String fullPath;

    private String avatarUrl;

    private String webUrl;
}
