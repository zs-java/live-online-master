package com.live.online.user.web.controller;

import com.live.online.common.core.exception.ApiResultException;
import com.live.online.common.core.result.entity.ApiResult;
import com.live.online.common.core.result.entity.WebResult;
import com.live.online.user.api.feign.UserServiceFeign;
import com.live.online.user.api.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱帅
 * @date 2020-08-10 12:42 上午
 */
@Api(tags = "用户模块")
@RestController
public class UserRestController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @ApiOperation(value = "根据ID获取用户信息")
    @GetMapping("/getUserById")
    public WebResult<UserDTO> getUserById(@RequestParam("id") Long id) throws ApiResultException {
        ApiResult<UserDTO> apiResult = userServiceFeign.getUserById(id);
        return WebResult.success(apiResult.getDataRequireSuccess());
    }

}
