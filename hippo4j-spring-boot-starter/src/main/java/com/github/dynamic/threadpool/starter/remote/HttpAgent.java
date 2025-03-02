package com.github.dynamic.threadpool.starter.remote;

import com.github.dynamic.threadpool.common.web.base.Result;

import java.util.Map;

/**
 * Http agent.
 *
 * @author chen.ma
 * @date 2021/6/23 20:45
 */
public interface HttpAgent {

    /**
     * Start.
     */
    void start();

    /**
     * Get tenant id.
     *
     * @return
     */
    String getTenantId();

    /**
     * Get encode.
     *
     * @return
     */
    String getEncode();

    /**
     * Send HTTP post request by discovery.
     *
     * @param path
     * @param body
     * @return
     */
    Result httpPostByDiscovery(String path, Object body);

    /**
     * Send HTTP get request by dynamic config.
     *
     * @param path
     * @param headers
     * @param paramValues
     * @param readTimeoutMs
     * @return
     */
    Result httpGetByConfig(String path, Map<String, String> headers, Map<String, String> paramValues,
                           long readTimeoutMs);

    /**
     * Send HTTP post request by dynamic config.
     *
     * @param path
     * @param headers
     * @param paramValues
     * @param readTimeoutMs
     * @return
     */
    Result httpPostByConfig(String path, Map<String, String> headers, Map<String, String> paramValues,
                            long readTimeoutMs);

    /**
     * Send HTTP delete request by dynamic config.
     *
     * @param path
     * @param headers
     * @param paramValues
     * @param readTimeoutMs
     * @return
     */
    Result httpDeleteByConfig(String path, Map<String, String> headers, Map<String, String> paramValues,
                              long readTimeoutMs);

}
