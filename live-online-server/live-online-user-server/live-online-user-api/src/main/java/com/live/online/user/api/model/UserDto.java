package com.live.online.user.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 朱帅
 * @date 2020-08-10 12:13 上午
 */
@Data
public class UserDto implements Serializable {

    private Long id;
    private String name;

}
