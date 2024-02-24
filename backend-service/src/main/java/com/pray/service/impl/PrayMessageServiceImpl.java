package com.pray.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pray.entity.po.PrayMessage;
import com.pray.mapper.PrayMessageMapper;
import com.pray.service.PrayMessageService;
import com.pray.utils.PrayConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author Rainy-Heights
* @description 针对表【pray_message(公告通知)】的数据库操作Service实现
* @createDate 2023-09-10 00:29:21
*/
@Service
public class PrayMessageServiceImpl extends ServiceImpl<PrayMessageMapper, PrayMessage> implements PrayMessageService{
    private static final DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 查询最近的热点数据，但是参数似乎有问题
     * @param
     * @return
     */
    @Resource
    private PrayMessageMapper msgMapper;
    @Override
    public List<PrayMessage> selectLatestPublish() {
       //查询近期消息
        List<PrayMessage> msg=msgMapper.getRecentMsg();
        redisTemplate.opsForValue().set(PrayConstants.PRAY_NOTICE,msg);
        return msg;
    }
}




