package com.alibaba.mos.interview;

import com.alibaba.mos.api.ItemService;
import com.alibaba.mos.api.SkuSimpleReadService;
import com.alibaba.mos.api.SkuStatisticsDataService;
import com.alibaba.mos.data.ItemDO;
import com.alibaba.mos.data.SkuDO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Interview1Tests {
    Logger log = LoggerFactory.getLogger(Interview1Tests.class);

    @Autowired
    SkuSimpleReadService skuSimpleReadService;
    @Autowired
    SkuStatisticsDataService skuStatisticsDataService;

    @Autowired
    ItemService itemService;

    /**
     * 试题1:
     * 在com.alibaba.mos.service.SkuSimpleReadServiceImpl中实现com.alibaba.mos.api
     * .SkuSimpleReadService#loadSkus()
     * 从/resources/data/skus.xls读取数据并返回对应数据实体
     */
    @Test
    void readSkuDataFromExcelTest() {
        List<SkuDO> list = skuSimpleReadService.loadSkus();
        log("一共读取条数:" + list.size());
        log(list);
        Assert.isTrue(CollectionUtils.isNotEmpty(list), "未能读取到sku数据列表");
    }

    /**
     * 试题2:
     * 计算以下统计值:
     * 1、价格在最中间 的skuId
     * 2、每个渠道库存量为前五的skuId列表 例如( miao:[1,2,3,4,5],tmall:[3,4,5,6,7],intime:[7,8,4,3,1]
     * 3、所有sku的总价值
     */
    @Test
    void statisticsDataTest() {
        List<SkuDO> list = skuSimpleReadService.loadSkus();
        Assert.isTrue(CollectionUtils.isNotEmpty(list), "未能读取到sku数据列表");
        // 1
        final String[] middlePriceSkuId =
                skuStatisticsDataService.findMiddlePriceSkuId(list);
        log("价格在最中间的skuId:");
        Arrays.stream(middlePriceSkuId).forEach(s -> log(" " + s));
        // 2
        final Map<String, List<String>> top5Sku =
                skuStatisticsDataService.top5Sku(list);
        log("TOP5:" + top5Sku);

        final BigDecimal totalPrice = skuStatisticsDataService.totalPrice(list);
        log("总价值:" + totalPrice);
        //TODO 自定义service和方法并实现以上注释功能
    }

    /**
     * 试题3:
     * 在com.alibaba.mos.service.ItemServiceImpl中实现com.alibaba.mos.service
     * .ItemService#aggregationSkus(java.util.List)
     * 读取sku列表并聚合为商品, 聚合规则为：
     * 对于sku type为原始商品(ORIGIN)的, 按货号(artNo)聚合成ITEM
     * 对于sku type为数字化商品(DIGITAL)的, 按spuId聚合成ITEM
     * 聚合结果需要包含: item的最大价格、最小价格、sku列表及全渠道总库存
     */
    @Test
    void aggregationSkusTest() {
        List<SkuDO> list = skuSimpleReadService.loadSkus();
        Assert.isTrue(CollectionUtils.isNotEmpty(list), "未能读取到sku数据列表");
        List<ItemDO> items = itemService.aggregationSkus(list);
        log(items);
        Assert.isTrue(CollectionUtils.isNotEmpty(items), "未能聚合商品列表");
    }

    private void log(Object... o) {
        for (Object i : o) {
            log.info(() -> String.valueOf(i));
        }
    }
}
