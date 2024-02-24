package com.pray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pray.entity.po.Role;
import com.pray.mapper.RoleMapper;
import com.pray.service.RoleService;
import com.pray.utils.PrayConstants;
import com.pray.utils.cache.RedisCacheUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
* @author Rainy-Heights
* @description 针对表【role】的数据库操作Service实现
* @createDate 2023-08-24 13:00:41
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    private static final ObjectMapper mapper=new ObjectMapper();
    private final ExecutorService executorService= Executors.newFixedThreadPool(10);
    //实现Redis缓存热点数据：
    public List listRole(int current, int pageSize) {
        String key = PrayConstants.REDIS_CACHE + "listRole:"+current+":"+pageSize;
        List<Role> listRole = (List<Role>) redisTemplate.opsForValue().get(key);
        //读取之前检查：在从Redis中读取数据之前，先检查数据库中是否存在该数据。如果数据库中不存在该数据，
        // 那么再从数据库中读取数据并存储到Redis中。这样可以确保Redis中的数据是最新的。
        if (listRole==null){
            Page<Role> page = getBaseMapper().selectPage(new Page<>(current, pageSize),
                    new LambdaQueryWrapper<Role>().orderByAsc(Role::getRoleCount));
            //注意：不要频繁的序列化和反序列化，可以直接返回
            //序列化查询到的数据
            //String jsonValue = mapper.writeValueAsString(page.getRecords());
            redisTemplate.opsForValue().set(key,
                    page.getRecords(),
                    2L,
                    TimeUnit.MINUTES);
            //反序列化查询的数据：
            //String redisJson = (String) redisTemplate.opsForValue().get(key);
            List<Role> res= (List<Role>) redisTemplate.opsForValue().get(key);
            return res;
        }
        return listRole;
    }

    /**
     * 更新缓存同时将数据同步到数据库里面
     * @param role
     * @return
     */
    @Override
    public String updatePageWithMutex(Role role) throws JsonProcessingException {
        List<Role> oldRoles = listRole(10, 20);
        String key = PrayConstants.REDIS_CACHE + "listRole";
        Boolean delete = redisTemplate.delete(key);
        if (Boolean.FALSE.equals(delete)){
            return "缓存删除错误,请重试";
        }else {
            //先更新缓存再更新数据库
            oldRoles.add(role);
            RedisCacheUpdate cacheUpdate=new RedisCacheUpdate(key,redisTemplate,oldRoles);
            //更新策略：将更新消息发到消息队列，异步更新；定时同步更新，将更新操作放到定时器里面
            executorService.submit(cacheUpdate);
            executorService.shutdown();
            save(role);
            return "更新数据成功";
        }
    }
}




