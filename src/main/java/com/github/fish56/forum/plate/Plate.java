package com.github.fish56.forum.plate;

import com.github.fish56.forum.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
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
     * icon的链接
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
