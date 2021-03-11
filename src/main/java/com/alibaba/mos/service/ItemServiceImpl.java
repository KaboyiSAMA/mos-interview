/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.service;

import com.alibaba.mos.api.ItemService;
import com.alibaba.mos.data.ItemDO;
import com.alibaba.mos.data.SkuDO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.mos.data.type.SkuType.*;
import static com.alibaba.mos.data.type.SkuType.ORIGIN;
import static java.lang.Math.max;

/**
 * @author superchao
 * @version $Id: ItemServiceImpl.java, v 0.1 2019年10月28日 11:11 AM superchao Exp $
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<ItemDO> aggregationSkus(List<SkuDO> skuList) {
        // 在此实现聚合sku的代码
        Map<String, ItemDO> map = new HashMap<>();
        skuList.stream().forEach(x -> {
            final ItemDO itemDO = map.computeIfAbsent(x.getArtNo(), y -> {
                final ItemDO i = new ItemDO();
                if (ORIGIN.name().equals(x.getSkuType())) {
                    i.setArtNo(x.getArtNo());
                } else if (DIGITAL.name().equals(x.getSkuType())) {
                    i.setSpuId(x.getSpuId());
                }
                return i;
            });
            itemDO.setMaxPrice(itemDO.getMaxPrice() != null ?
                    (itemDO.getMaxPrice().compareTo(x.getPrice()) > 0 ?
                            itemDO.getMaxPrice() :
                            x.getPrice()) :
                    x.getPrice());
            itemDO.setMinPrice(itemDO.getMinPrice() != null ?
                    (itemDO.getMinPrice().compareTo(x.getPrice()) < 0 ?
                            itemDO.getMinPrice() :
                            x.getPrice()) :
                    x.getPrice());
            List<String> skuIds = itemDO.getSkuIds();
            if (skuIds == null) {
                skuIds = new ArrayList<>();
                itemDO.setSkuIds(skuIds);
                itemDO.setName(x.getName());
            }
            skuIds.add(x.getId());
            BigDecimal inventory = itemDO.getInventory();
            if (inventory == null) {
                inventory = new BigDecimal(0);
            }
            inventory = inventory.add(
                    x.getInventoryList().stream().map(y -> y.getInventory()).reduce((l,
                                                                                     r) -> l.add(r)).get());
            itemDO.setInventory(inventory);
        });
        List<ItemDO> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }
}