package com.live.online.user.service.impl;

import com.live.online.im.api.feign.ImServiceFeign;
import com.live.online.user.api.model.UserDto;
import com.live.online.user.api.service.IUserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 朱帅
 * @date 2020-08-10 12:28 上午
 */
@RestController
public class UserApiServiceImpl implements IUserApiService {

    @Autowired
    private ImServiceFeign imServiceFeign;

    /**
     * 测试
     * @param id id
     * @return user
     */
    @PostMapping("/getUserById")
    public UserDto getUserById(@RequestParam("id") Long id) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(imServiceFeign.test());
        return userDto;
    }

}
