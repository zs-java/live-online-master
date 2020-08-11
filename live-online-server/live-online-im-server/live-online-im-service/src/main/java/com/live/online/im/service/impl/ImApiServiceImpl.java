package com.live.online.im.service.impl;

import com.live.online.im.api.service.IImApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱帅
 * @date 2020-08-10 1:15 上午
 */
@RestController
public class ImApiServiceImpl implements IImApiService {

    @Value("${im.message:}")
    private String message;

    @GetMapping("/test")
    public String test() {
        return "Config:" + message;
    }

}
