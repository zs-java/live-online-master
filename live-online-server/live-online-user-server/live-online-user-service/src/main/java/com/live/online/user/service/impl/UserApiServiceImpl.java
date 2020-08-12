package com.live.online.user.service.impl;

import com.live.online.common.base.exception.ApiResultException;
import com.live.online.common.base.result.entity.ApiResult;
import com.live.online.im.api.feign.ImServiceFeign;
import com.live.online.user.api.model.UserDTO;
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
    @Override
    @PostMapping("/getUserById")
    public ApiResult<UserDTO> getUserById(@RequestParam("id") Long id) throws ApiResultException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(imServiceFeign.test().getDataRequireSuccess());
        return ApiResult.success(userDTO);
    }

}
