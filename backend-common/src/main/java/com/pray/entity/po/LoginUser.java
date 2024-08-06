package com.pray.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户表
 * @author Rainy-Heights
 * @TableName user
 */
@TableName(value ="user")//添加数据库表名前缀
@ToString
public class LoginUser implements Serializable, UserDetails {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户唯一id,设置为自增主键，看来是设置了一个之后自动进行表id的 检验了
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String email;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //权限认证，不会存在于表里面，主要是去写AuthorizationFilter
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    /*
    * getAuthorities()	获取当前用户的所有角色信息
        getPassword()	获取当前用户的密码
        getUsername()	获取当前用户的用户名
        isAccountNonExpired()	当前账户是否未过期
        isAccountNonLocked()	当前账户是否未锁定
        isCredentialsNonExpired()	当前账户密码是否未过期
        isEnabled()	当前账户是否可用
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
