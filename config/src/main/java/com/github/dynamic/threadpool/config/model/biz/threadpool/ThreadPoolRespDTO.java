package com.github.dynamic.threadpool.config.model.biz.threadpool;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Thread pool resp dto.
 *
 * @author chen.ma
 * @date 2021/6/30 21:23
 */
@Data
public class ThreadPoolRespDTO {

    /**
     * tenantId
     */
    private String tenantId;

    /**
     * itemId
     */
    private String itemId;

    /**
     * tpId
     */
    private String tpId;

    /**
     * content
     */
    private String content;

    /**
     * coreSize
     */
    private Integer coreSize;

    /**
     * maxSize
     */
    private Integer maxSize;

    /**
     * queueType
     */
    private Integer queueType;

    /**
     * queueName
     */
    private String queueName;

    /**
     * capacity
     */
    private Integer capacity;

    /**
     * keepAliveTime
     */
    private Integer keepAliveTime;

    /**
     * isAlarm
     */
    private Integer isAlarm;

    /**
     * capacityAlarm
     */
    private Integer capacityAlarm;

    /**
     * livenessAlarm
     */
    private Integer livenessAlarm;

    /**
     * rejectedType
     */
    private Integer rejectedType;

    /**
     * gmtCreate
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * gmtModified
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
