package com.cfm.datasource.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cfm.datasource.annotation.DataSource;
import com.cfm.datasource.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: SysUserMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/18 10:33
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @DataSource(value = "cnr_core")
    List<SysUser> getAllUserInfo();

    @DataSource(value = "test_db")
    List<SysUser> getAllUserInfo1();
}
