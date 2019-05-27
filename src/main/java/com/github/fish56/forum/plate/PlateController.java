package com.github.fish56.forum.plate;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.service.ServerResponseMessage;
import com.github.fish56.forum.user.User;
import com.github.fish56.forum.validate.ShouldValidate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/plates")
public class PlateController {

    @Autowired
    private PlateRepos plateRepos;

    @ApiOperation("获得所有版块的列表")
    @GetMapping
    public List<Plate> getPlateList(){
        return plateRepos.findAll();
    }

    @ApiOperation("创建一个版块")
    @PostMapping
    public ResponseEntity<Plate> postPlate(@ShouldValidate @RequestBody Plate plate,
                                           @RequestAttribute User user){
        plate.setAdmin(user);
        plate.setId(null);
        plateRepos.save(plate);
        return ResponseEntity.status(201).body(plate);
    }

    @ApiOperation("修改版块的信息")
    @RequestMapping(value = "/{plateId}", method = RequestMethod.PATCH)
    public Object editPlates(@PathVariable Integer plateId,
                             @ShouldValidate @RequestBody Plate plate,
                             @RequestAttribute User user){
        log.info("用户" + user.getId() + "正在尝试修改版块" + plateId + "的信息");

        // 查询目标版块的信息
        Optional<Plate> optionalPlate = plateRepos.findById(plateId);

        // 说明数据库中没有对应ID的数据
        if (!optionalPlate.isPresent()){
            return ServerResponseMessage.get(404, "目标版块不存在");
        }

        // 这个是数据库中这个版块的信息
        Plate plateInDB = optionalPlate.get();

        // 判断当前用户是否是版块管理员
        if (!plateInDB.getAdmin().getId().equals(user.getId())){
            return ServerResponseMessage.get(401, "只有版块管理员可以修改版块信息");
        }

        // 将记录插入数据库中
        // 标题默认无法修改
        plateInDB.setInfo(plate.getInfo());
        plateInDB.setIcon(plate.getIcon());
        return plateRepos.save(plateInDB);
    }
}
