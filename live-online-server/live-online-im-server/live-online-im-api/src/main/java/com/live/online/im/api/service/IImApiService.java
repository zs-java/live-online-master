package com.live.online.im.api.service;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 朱帅
 * @date 2020-08-10 1:06 上午
 */
public interface IImApiService {

    @GetMapping("/test")
    String test();

}
