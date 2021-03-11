/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.api;

import com.alibaba.mos.data.SkuDO;

import java.util.List;

/**
 *
 * @author superchao
 * @version $Id: SkuReadService.java, v 0.1 2019年10月28日 10:45 AM superchao Exp $
 */
public interface SkuSimpleReadService {

    /**
     * 从excel读取sku数据
     * @return
     */
    List<SkuDO> loadSkus();
}