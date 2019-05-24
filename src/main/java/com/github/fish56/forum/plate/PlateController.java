package com.github.fish56.forum.plate;

import com.github.fish56.forum.service.ErrorResponseEntity;
import com.github.fish56.forum.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Plate> postPlate(@RequestBody Plate plate, @RequestAttribute User user){
        plate.setAdmin(user);
        plate.setId(null);
        plateRepos.save(plate);
        return ResponseEntity.status(201).body(plate);
    }

    @ApiOperation("修改版块的信息")
    @PatchMapping
    public Object editPlates(@RequestBody Plate plate, @RequestAttribute User user){

        // 参数中没有字段，说明这是插入数据的请求
        if (plate.getId() == null) {
            return ErrorResponseEntity.get(400, "请传递目标版块的id");
        }
        Optional<Plate> optionalPlate = plateRepos.findById(plate.getId());

        // 说明数据库中没有对应ID的数据
        if (!optionalPlate.isPresent()){
            return ErrorResponseEntity.get(404, "目标版块不存在");
        }

        Plate plateInDB = optionalPlate.get();

        // 判断当前用户是否是版块管理员
        if (!plateInDB.getAdmin().getId().equals(user.getId())){
            return ErrorResponseEntity.get(401, "只有版块管理员可以修改版块形象");
        }

        // 将记录插入数据库中
        // 标题默认无法修改
        plateInDB.setInfo(plate.getInfo());
        plateInDB.setIcon(plate.getIcon());
        return plateRepos.save(plateInDB);
    }
}
