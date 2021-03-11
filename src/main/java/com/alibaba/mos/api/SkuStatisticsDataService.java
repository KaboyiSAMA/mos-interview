package com.alibaba.mos.api;

import com.alibaba.mos.data.SkuDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Kaboyi
 */
public interface SkuStatisticsDataService {
    String[] findMiddlePriceSkuId(List<SkuDO> list);
    Map<String, List<String>> top5Sku(List<SkuDO> list);
    BigDecimal totalPrice(List<SkuDO> list);
}
