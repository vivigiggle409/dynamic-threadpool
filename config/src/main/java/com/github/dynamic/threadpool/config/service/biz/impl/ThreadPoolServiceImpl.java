package com.github.dynamic.threadpool.config.service.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dynamic.threadpool.config.enums.DelEnum;
import com.github.dynamic.threadpool.config.mapper.ConfigInfoMapper;
import com.github.dynamic.threadpool.config.model.ConfigAllInfo;
import com.github.dynamic.threadpool.config.model.biz.threadpool.ThreadPoolQueryReqDTO;
import com.github.dynamic.threadpool.config.model.biz.threadpool.ThreadPoolRespDTO;
import com.github.dynamic.threadpool.config.model.biz.threadpool.ThreadPoolSaveOrUpdateReqDTO;
import com.github.dynamic.threadpool.config.service.biz.ConfigService;
import com.github.dynamic.threadpool.config.service.biz.ThreadPoolService;
import com.github.dynamic.threadpool.config.toolkit.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Thread pool service impl.
 *
 * @author chen.ma
 * @date 2021/6/30 21:26
 */
@Service
@AllArgsConstructor
public class ThreadPoolServiceImpl implements ThreadPoolService {

    private final ConfigService configService;

    private final ConfigInfoMapper configInfoMapper;

    @Override
    public IPage<ThreadPoolRespDTO> queryThreadPoolPage(ThreadPoolQueryReqDTO reqDTO) {
        LambdaQueryWrapper<ConfigAllInfo> wrapper = Wrappers.lambdaQuery(ConfigAllInfo.class)
                .eq(!StringUtils.isBlank(reqDTO.getTenantId()), ConfigAllInfo::getTenantId, reqDTO.getTenantId())
                .eq(!StringUtils.isBlank(reqDTO.getItemId()), ConfigAllInfo::getItemId, reqDTO.getItemId())
                .eq(!StringUtils.isBlank(reqDTO.getTpId()), ConfigAllInfo::getTpId, reqDTO.getTpId())
                .eq(ConfigAllInfo::getDelFlag, DelEnum.NORMAL);

        return configInfoMapper.selectPage(reqDTO, wrapper).convert(each -> BeanUtil.convert(each, ThreadPoolRespDTO.class));
    }

    @Override
    public ThreadPoolRespDTO getThreadPool(ThreadPoolQueryReqDTO reqDTO) {
        ConfigAllInfo configAllInfo = configService.findConfigAllInfo(reqDTO.getTpId(), reqDTO.getItemId(), reqDTO.getTenantId());
        return BeanUtil.convert(configAllInfo, ThreadPoolRespDTO.class);
    }

    @Override
    public List<ThreadPoolRespDTO> getThreadPoolByItemId(String itemId) {
        LambdaQueryWrapper<ConfigAllInfo> queryWrapper = Wrappers.lambdaQuery(ConfigAllInfo.class)
                .eq(ConfigAllInfo::getItemId, itemId);

        List<ConfigAllInfo> selectList = configInfoMapper.selectList(queryWrapper);
        return BeanUtil.convert(selectList, ThreadPoolRespDTO.class);
    }

    @Override
    public void saveOrUpdateThreadPoolConfig(String identify, ThreadPoolSaveOrUpdateReqDTO reqDTO) {
        configService.insertOrUpdate(identify, BeanUtil.convert(reqDTO, ConfigAllInfo.class));
    }

}
