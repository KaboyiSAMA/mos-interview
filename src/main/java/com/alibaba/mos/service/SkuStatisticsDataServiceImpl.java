package com.alibaba.mos.service;

import com.alibaba.mos.api.SkuStatisticsDataService;
import com.alibaba.mos.data.SkuDO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kaboyi
 */
@Service
public class SkuStatisticsDataServiceImpl implements SkuStatisticsDataService {

    @Override
    public String[] findMiddlePriceSkuId(List<SkuDO> list) {
        list.sort((x, y) -> x.getPrice().compareTo(y.getPrice()));
        final int size = list.size();
        final int mid = size / 2;
        final BigDecimal midPrice = list.get(mid).getPrice();
        Predicate<SkuDO> skuDOPredicate = x -> x.getPrice().equals(midPrice);
        if (size % 2 == 0) {
            Integer mid2 = mid + 1;
            final BigDecimal midPrice2 = list.get(mid2).getPrice();
            skuDOPredicate = skuDOPredicate.or(x -> x.getPrice().equals(midPrice2));
        }
        return list.stream().filter(skuDOPredicate).map(x -> x.getId()).collect(Collectors.toList()).toArray(new String[0]);
    }

    @Override
    public Map<String, List<String>> top5Sku(List<SkuDO> list) {
        String[] keys = new String[]{"MIAO", "TMALL", "INTIME"};
        Map<String, Map<BigDecimal, String>> map = new HashMap<>();
        for (SkuDO skuDO : list) {
            skuDO.getInventoryList().forEach(x -> {
                for (String key : keys) {
                    final Map<BigDecimal, String> invs = map.computeIfAbsent(key,
                            y -> new TreeMap<>((a, b) -> b.compareTo(a)));
                    if (key.equals(x.getChannelCode())) {
                        invs.put(x.getInventory(), skuDO.getId());
                    }
                }
            });
        }
        Map<String, List<String>> ret = new HashMap<>();
        for (Map.Entry<String, Map<BigDecimal, String>> entry : map.entrySet()) {
            ret.put(entry.getKey(),
                    entry.getValue().values().stream().limit(5).collect(Collectors.toList()));
        }
        return ret;
    }

    @Override
    public BigDecimal totalPrice(List<SkuDO> list) {
        return list.stream().map(x -> x.getPrice().multiply(x.getInventoryList().stream().map(y -> y.getInventory()).reduce((l, r) -> l.add(r)).get())).reduce((p, l) -> p.add(l)).get();
    }
}
