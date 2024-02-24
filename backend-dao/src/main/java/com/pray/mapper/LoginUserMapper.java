package com.pray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.entity.po.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author Rainy-Heights
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-08-24 13:00:41
* @Entity com.pray.entity.po.User
*/
@Repository
public interface LoginUserMapper extends BaseMapper<LoginUser> {
    @Select("select * from bootdemo.user where username=#{username}")
    LoginUser loadUserByUsername(@Param("username") String username);
}




