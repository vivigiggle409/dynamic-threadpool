package com.github.dynamic.threadpool.config.service.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.dynamic.threadpool.common.toolkit.Assert;
import com.github.dynamic.threadpool.config.enums.DelEnum;
import com.github.dynamic.threadpool.config.mapper.TenantInfoMapper;
import com.github.dynamic.threadpool.config.model.TenantInfo;
import com.github.dynamic.threadpool.config.model.biz.item.ItemQueryReqDTO;
import com.github.dynamic.threadpool.config.model.biz.item.ItemRespDTO;
import com.github.dynamic.threadpool.config.model.biz.tenant.TenantQueryReqDTO;
import com.github.dynamic.threadpool.config.model.biz.tenant.TenantRespDTO;
import com.github.dynamic.threadpool.config.model.biz.tenant.TenantSaveReqDTO;
import com.github.dynamic.threadpool.config.model.biz.tenant.TenantUpdateReqDTO;
import com.github.dynamic.threadpool.config.service.biz.ItemService;
import com.github.dynamic.threadpool.config.service.biz.TenantService;
import com.github.dynamic.threadpool.config.toolkit.BeanUtil;
import com.github.dynamic.threadpool.logrecord.annotation.LogRecord;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Tenant service impl.
 *
 * @author chen.ma
 * @date 2021/6/29 21:12
 */
@Service
@AllArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final ItemService itemService;

    private final TenantInfoMapper tenantInfoMapper;

    @Override
    public TenantRespDTO getTenantById(String id) {
        return BeanUtil.convert(tenantInfoMapper.selectById(id), TenantRespDTO.class);
    }

    @Override
    public TenantRespDTO getTenantByTenantId(String tenantId) {
        LambdaQueryWrapper<TenantInfo> queryWrapper = Wrappers
                .lambdaQuery(TenantInfo.class).eq(TenantInfo::getTenantId, tenantId);

        TenantInfo tenantInfo = tenantInfoMapper.selectOne(queryWrapper);
        TenantRespDTO result = BeanUtil.convert(tenantInfo, TenantRespDTO.class);
        return result;
    }

    @Override
    public IPage<TenantRespDTO> queryTenantPage(TenantQueryReqDTO reqDTO) {
        LambdaQueryWrapper<TenantInfo> wrapper = Wrappers.lambdaQuery(TenantInfo.class)
                .eq(!StringUtils.isEmpty(reqDTO.getTenantId()), TenantInfo::getTenantId, reqDTO.getTenantId())
                .eq(!StringUtils.isEmpty(reqDTO.getTenantName()), TenantInfo::getTenantName, reqDTO.getTenantName())
                .eq(!StringUtils.isEmpty(reqDTO.getOwner()), TenantInfo::getOwner, reqDTO.getOwner());

        Page resultPage = tenantInfoMapper.selectPage(reqDTO, wrapper);
        return resultPage.convert(each -> BeanUtil.convert(each, TenantRespDTO.class));
    }

    @Override
    public void saveTenant(TenantSaveReqDTO reqDTO) {
        TenantInfo tenantInfo = BeanUtil.convert(reqDTO, TenantInfo.class);
        int insertResult = tenantInfoMapper.insert(tenantInfo);

        boolean retBool = SqlHelper.retBool(insertResult);
        if (!retBool) {
            throw new RuntimeException("Save Error.");
        }
    }

    @Override
    @LogRecord(
            prefix = "item",
            bizNo = "{{#reqDTO.tenantId}}_{{#reqDTO.tenantName}}",
            category = "TENANT_UPDATE",
            success = "更新租户, ID :: {{#reqDTO.id}}, 租户名称由 :: {TENANT{#reqDTO.id}} -> {{#reqDTO.tenantName}}",
            detail = "{{#reqDTO.toString()}}"
    )
    public void updateTenant(TenantUpdateReqDTO reqDTO) {
        TenantInfo tenantInfo = BeanUtil.convert(reqDTO, TenantInfo.class);
        int updateResult = tenantInfoMapper.update(tenantInfo, Wrappers
                .lambdaUpdate(TenantInfo.class).eq(TenantInfo::getTenantId, reqDTO.getTenantId()));

        boolean retBool = SqlHelper.retBool(updateResult);
        if (!retBool) {
            throw new RuntimeException("Update Error.");
        }
    }

    @Override
    public void deleteTenantById(String tenantId) {
        ItemQueryReqDTO reqDTO = new ItemQueryReqDTO();
        reqDTO.setTenantId(tenantId);
        List<ItemRespDTO> itemList = itemService.queryItem(reqDTO);
        if (CollectionUtils.isNotEmpty(itemList)) {
            throw new RuntimeException("The line of business contains project references, and the deletion failed.");
        }

        int updateResult = tenantInfoMapper.update(new TenantInfo(),
                Wrappers.lambdaUpdate(TenantInfo.class)
                        .eq(TenantInfo::getTenantId, tenantId)
                        .set(TenantInfo::getDelFlag, DelEnum.DELETE.getIntCode()));

        boolean retBool = SqlHelper.retBool(updateResult);
        if (!retBool) {
            throw new RuntimeException("Delete error.");
        }
    }
}
