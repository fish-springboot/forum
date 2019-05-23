package com.github.fish56.forum.plate;

import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class PlateServiceImplTest extends ForumApplicationTests {
    @Autowired
    private PlateService plateService;

    @Test
    public void createOrUpdatePlate() {
        Plate plate = new Plate();
        plate.setTitle("koa");
        plate.setInfo("王者荣耀");
        User user = new User();
        user.setId(1);
        plate.setAdmin(user);

        ServiceResponse serviceResponse = plateService.createOrUpdatePlate(plate);
        if (serviceResponse.hasError()){
            log.info(serviceResponse.getErrorMessage());
        } else {
            log.info(serviceResponse.getData().toString());
        }
    }
}