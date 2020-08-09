package com.live.online.user.api.feign;

import com.live.online.common.constants.AppName;
import com.live.online.user.api.service.IUserApiService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 朱帅
 * @date 2020-08-10 12:16 上午
 */
@FeignClient(AppName.USER_SERVICE_APP)
public interface UserServiceFeign extends IUserApiService {
}