package com.live.online.test.oss;

import com.live.online.im.ImServiceApp;
import com.live.online.oss.api.OssUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 朱帅
 * @date 2020-08-16 2:24 下午
 */
@SpringBootTest(classes = ImServiceApp.class)
@RunWith(SpringRunner.class)
public class OssTest {

    @Autowired
    private OssUtils ossUtils;

    @Test
    public void uploadTest() {
        String url = ossUtils.upload(null, "live-online", "1.png");
        System.out.println(url);
    }

}
