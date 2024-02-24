package com.pray.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pray.entity.po.Weapon;
import com.pray.mapper.WeaponMapper;
import com.pray.service.WeaponService;
import org.springframework.stereotype.Service;

/**
* @author Rainy-Heights
* @description 针对表【weapon】的数据库操作Service实现
* @createDate 2023-08-24 13:00:41
*/
@Service
public class WeaponServiceImpl extends ServiceImpl<WeaponMapper, Weapon>
    implements WeaponService{

}




