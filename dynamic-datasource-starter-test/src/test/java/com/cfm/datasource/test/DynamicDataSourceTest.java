package com.cfm.datasource.test;

import com.alibaba.fastjson.JSONObject;
import com.cfm.datasource.TestApplication;
import com.cfm.datasource.entity.SysUser;
import com.cfm.datasource.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSourceTest
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/18 14:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DynamicDataSourceTest {

    @Autowired
    SysUserMapper userMapper;

    @Test
    public void test(){
        List<SysUser> userList = userMapper.getAllUserInfo();
        List<SysUser> sysUsers = userMapper.getAllUserInfo1();
        System.out.println(JSONObject.toJSONString("cnr_core:"+userList));

        System.out.println(JSONObject.toJSONString("test_db:"+userList));
    }
}
