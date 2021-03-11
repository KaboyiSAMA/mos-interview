/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.api;

import com.alibaba.mos.data.ItemDO;

import java.util.List;

/**
 *
 * @author superchao
 * @version $Id: ItemAggregationProviderConsumer.java, v 0.1 2019年10月28日 12:00 PM superchao Exp $
 */
public interface ProviderConsumer<T> {

    /**
     * 开始生产线消费任务
     */
    void execute();

    /**
     * 回调最终聚合结果
     * @param data
     */
    void callbck(T data);
}