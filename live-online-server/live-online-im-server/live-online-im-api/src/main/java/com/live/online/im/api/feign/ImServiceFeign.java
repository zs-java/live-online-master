package com.live.online.im.api.feign;

import com.live.online.common.base.constants.AppName;
import com.live.online.im.api.service.IImApiService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 朱帅
 * @date 2020-08-10 1:11 上午
 */
@FeignClient(AppName.IM_SERVICE_APP)
public interface ImServiceFeign extends IImApiService {
}
