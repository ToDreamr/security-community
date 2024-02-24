package com.pray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.entity.po.PrayMessage;
import com.pray.utils.PrayConstants;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Rainy-Heights
* @description 针对表【pray_message(公告通知)】的数据库操作Mapper
* @createDate 2023-09-10 00:29:21
* @Entity com.pray.entity.po.PrayMessage
*/
@Repository
public interface PrayMessageMapper extends BaseMapper<PrayMessage> {
    int duration=PrayConstants.RECENT_DAYS;
    @Select("select * from pray_message where datediff(publish_time,timestamp (now()))<"+duration)
    List<PrayMessage> getRecentMsg();
}




