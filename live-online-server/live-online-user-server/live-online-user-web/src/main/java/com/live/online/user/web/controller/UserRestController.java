package com.live.online.user.web.controller;

import com.live.online.user.api.feign.UserServiceFeign;
import com.live.online.user.api.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱帅
 * @date 2020-08-10 12:42 上午
 */
@RestController
public class UserRestController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @GetMapping("/getUserById")
    public UserDto getUserById(@RequestParam("id") Long id) {
        return userServiceFeign.getUserById(id);
    }

}
