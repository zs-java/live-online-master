package com.live.online.user.api.service;

import com.live.online.user.api.model.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 朱帅
 * @date 2020-08-10 12:11 上午
 */
public interface IUserApiService {

    /**
     * get user by id
     * @param id id
     * @return user
     */
    @PostMapping("/getUserById")
    UserDto getUserById(@RequestParam("id") Long id);

}
