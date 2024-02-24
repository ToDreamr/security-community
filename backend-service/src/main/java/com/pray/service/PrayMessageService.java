package com.pray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pray.entity.po.PrayMessage;

import java.util.List;

/**
* @author Rainy-Heights
* @description 针对表【pray_message(公告通知)】的数据库操作Service
* @createDate 2023-09-10 00:29:21
*/
public interface PrayMessageService extends IService<PrayMessage> {

    List<PrayMessage> selectLatestPublish() throws JsonProcessingException;
}
