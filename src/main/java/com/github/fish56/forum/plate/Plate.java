package com.github.fish56.forum.plate;

import com.github.fish56.forum.user.User;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 版块实体
 *   - 所有的文章都存在于特定的版块
 *   - 每个版块都以一个创建者作为管理员
 *   - 管理员可以修改一个文章的便签，但是不能修改文章的内容
 */
@Entity
@Data
@Accessors(chain = true)
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * 版块详情介绍
     */
    private String info;

    /**
     * 版块的管理员，默认是创建者
     */
    @OneToOne
    private User admin;

    /**
     * 版块小图标的链接
     */
    private String icon;

    public void updateByPlate(Plate plate){
        if (plate.getTitle() != null) {
            title = plate.getTitle();
        }
        if (plate.getAdmin() != null) {
            admin = plate.getAdmin();
        }
        if (plate.getInfo() != null) {
            info = plate.getInfo();
        }
        if (plate.getIcon() != null) {
            icon = plate.getIcon();
        }
    }
}
