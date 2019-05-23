package com.github.fish56.forum.plate;

import com.github.fish56.forum.user.User;
import com.github.fish56.forum.user.UserRepos;
import com.github.fish56.forum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plates")
public class PlateController {
    @Autowired
    private PlateService plateService;

    @Autowired
    private PlateRepos plateRepos;

    @GetMapping
    public List<Plate> plates(){
        return plateRepos.findAll();
    }
}
