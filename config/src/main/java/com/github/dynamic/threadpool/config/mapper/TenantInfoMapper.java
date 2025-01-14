package com.github.dynamic.threadpool.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.dynamic.threadpool.config.model.TenantInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Tenant info mapper.
 *
 * @author chen.ma
 * @date 2021/6/29 22:44
 */
@Mapper
public interface TenantInfoMapper extends BaseMapper<TenantInfo> {
}
