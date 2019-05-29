package com.github.fish56.forum.plate;

import com.github.fish56.forum.service.ServerResponseMessage;
import com.github.fish56.forum.user.User;
import com.github.fish56.forum.validate.ShouldValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/plates")
public class PlateController {

    @Autowired
    private PlateRepos plateRepos;

    @ApiOperation("获得所有版块的列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功获得版块列表",
                    response = Plate.class, responseContainer = "List")
    })
    @GetMapping
    public List<Plate> getPlateList(){
        return plateRepos.findAll();
    }

    @ApiOperation("创建一个版块")

    @ApiResponses({
            @ApiResponse(code = 201, message = "成功创建一个版块", response = Plate.class)
    })
    @PostMapping
    public ResponseEntity<Plate> postPlate(@ShouldValidate(ShouldValidate.OnCreate.class)
                                               @RequestBody PlateDTO plateDTO,
                                           @ApiIgnore @RequestAttribute User user){

        Plate plate = new Plate();
        plate.updateByDTO(plateDTO);
        plate.setAdmin(user);
        plateRepos.save(plate);
        return ResponseEntity.status(201).body(plate);
    }

    @ApiOperation("修改版块的信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功修改一个版块", response = Plate.class)
    })
    @RequestMapping(value = "/{plateId}", method = RequestMethod.PATCH)
    public Object editPlates(@PathVariable Integer plateId,
                             @ShouldValidate @RequestBody PlateDTO plateDTO,
                             @ApiIgnore @RequestAttribute User user){
        log.info("用户" + user.getId() + "正在尝试修改版块" + plateId + "的信息");

        // 查询目标版块的信息
        Optional<Plate> optionalPlate = plateRepos.findById(plateId);

        // 说明数据库中没有对应ID的数据
        if (!optionalPlate.isPresent()){
            return ServerResponseMessage.get(404, "目标版块不存在");
        }

        // 这个是数据库中这个版块的信息
        Plate plate = optionalPlate.get();

        // 判断当前用户是否是版块管理员
        if (!plate.getAdmin().getId().equals(user.getId())){
            return ServerResponseMessage.get(401, "只有版块管理员可以修改版块信息");
        }

        // 将记录插入数据库中
        // 标题默认无法修改
        plate.updateByDTO(plateDTO);
        return plateRepos.save(plate);
    }
}
