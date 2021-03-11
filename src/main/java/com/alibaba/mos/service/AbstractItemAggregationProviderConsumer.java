/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.service;

import com.alibaba.mos.api.ProviderConsumer;
import com.alibaba.mos.data.ItemDO;

import java.util.List;

/**
 *
 * @author superchao
 * @version $Id: ItemAggregationProviderConsumerImpl.java, v 0.1 2019年10月28日 12:01 PM superchao Exp $
 */
public abstract class AbstractItemAggregationProviderConsumer implements ProviderConsumer<List<ItemDO>> {

    @Override
    public void execute() {

    }

    @Override
    public abstract void callbck(List<ItemDO> data);
}