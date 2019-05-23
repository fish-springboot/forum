package com.github.fish56.forum.plate;

import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlateServiceImpl implements PlateService {
    @Autowired
    private PlateRepos plateRepos;

    @Override
    public ServiceResponse createOrUpdatePlate(Plate plate) {
        // 参数中没有字段，说明这是插入数据的请求
        if (plate.getId() == null) {
            plateRepos.save(plate);
            return ServiceResponse.getInstance(plate);
        }
        Optional<Plate> optionalPlate = plateRepos.findById(plate.getId());

        // 说明数据库中没有对应ID的数据
        if (!optionalPlate.isPresent()){
            plateRepos.save(plate);
            return ServiceResponse.getInstance(plate);
        }

        Plate plateInDB = optionalPlate.get();
        plateInDB.updateByPlate(plate);
        plateRepos.save(plate);
        return ServiceResponse.getInstance(plate);
    }
}
