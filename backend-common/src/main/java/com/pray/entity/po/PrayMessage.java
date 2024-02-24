package com.pray.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pray.common.LocalDateSerialization;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公告通知
 * @TableName pray_message
 */
@TableName(value ="pray_message")
@Data
public class PrayMessage implements Serializable {
    /**
     * 管理员姓名
     */
    private String administratorName;

    /**
     * 管理员ID
     */
    @TableId(value = "administrator_id",type = IdType.AUTO)
    private Integer administratorId;


    /**
     * 公告内容
     */
    private String contents;
    /**
     * 发布时间
     */
    @JsonSerialize(using = LocalDateSerialization.class)
    private LocalDateTime publishTime;
    @JsonSerialize(using = LocalDateSerialization.class)
    public LocalDateTime getPublishTime() {
        return publishTime;
    }
    @JsonSerialize(using = LocalDateSerialization.class)
    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getExpireTime() {
        return expireTime.toString();
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
