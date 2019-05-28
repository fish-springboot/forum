package com.github.fish56.forum.plate;

import com.github.fish56.forum.validate.ShouldValidate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 封装用户上传的数据
 * 创建数据和修改数据用到的API不一样。。
 */
@Data
@Accessors(chain = true)
public class PlateDTO {

    @NotNull(groups = ShouldValidate.OnCreate.class, message = "创建Plate的时候必须设置title")
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * 版块详情介绍
     */
    private String info;

    /**
     * 版块小图标的链接
     */
    private String icon;

}
