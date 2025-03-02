package com.github.dynamic.threadpool.common.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Instance info.
 *
 * @author chen.ma
 * @date 2021/7/13 22:10
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class InstanceInfo {

    private static final String UNKNOWN = "unknown";

    private String appName = UNKNOWN;

    private String hostName;

    private String port;

    private String instanceId;

    private String ipApplicationName;

    private String clientBasePath;

    private String callBackUrl;

    private String identify;

    private volatile String vipAddress;

    private volatile String secureVipAddress;

    private volatile ActionType actionType;

    private volatile boolean isInstanceInfoDirty = false;

    private volatile Long lastUpdatedTimestamp;

    private volatile Long lastDirtyTimestamp;

    private volatile InstanceStatus status = InstanceStatus.UP;

    private volatile InstanceStatus overriddenStatus = InstanceStatus.UNKNOWN;

    public InstanceInfo() {
        this.lastUpdatedTimestamp = System.currentTimeMillis();
        this.lastDirtyTimestamp = lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp() {
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    public Long getLastDirtyTimestamp() {
        return lastDirtyTimestamp;
    }

    public synchronized void setOverriddenStatus(InstanceStatus status) {
        if (this.overriddenStatus != status) {
            this.overriddenStatus = status;
        }
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public synchronized void setIsDirty() {
        isInstanceInfoDirty = true;
        lastDirtyTimestamp = System.currentTimeMillis();
    }

    public synchronized long setIsDirtyWithTime() {
        setIsDirty();
        return lastDirtyTimestamp;
    }

    public synchronized void unsetIsDirty(long unsetDirtyTimestamp) {
        if (lastDirtyTimestamp <= unsetDirtyTimestamp) {
            isInstanceInfoDirty = false;
        }
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public enum InstanceStatus {

        UP,

        DOWN,

        STARTING,

        OUT_OF_SERVICE,

        UNKNOWN;

        public static InstanceStatus toEnum(String s) {
            if (s != null) {
                try {
                    return InstanceStatus.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // ignore and fall through to unknown
                    log.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
                }
            }
            return UNKNOWN;
        }
    }

    public enum ActionType {
        ADDED,

        MODIFIED,

        DELETED
    }

    @Data
    @Accessors(chain = true)
    public static class InstanceRenew {

        private String appName;

        private String instanceId;

        private String lastDirtyTimestamp;

        private String status;

    }

}

