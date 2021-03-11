/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.api;

import com.alibaba.mos.data.ItemDO;
import com.alibaba.mos.data.SkuDO;

import java.util.List;

/**
 *
 * @author superchao
 * @version $Id: ItemService.java, v 0.1 2019年10月28日 10:59 AM superchao Exp $
 */
public interface ItemService {
    /**
     * 对sku进行聚合, 聚合规则为：
     * 对于sku type为原始商品(ORIGIN)的, 按货号(artNo)聚合成ITEM
     * 对于sku type为数字化商品(DIGITAL)的, 按spuId聚合成ITEM
     * @param skuList
     * @return
     */
    List<ItemDO> aggregationSkus(List<SkuDO> skuList);
}