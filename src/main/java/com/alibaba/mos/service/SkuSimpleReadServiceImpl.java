/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.mos.api.SkuSimpleReadService;
import com.alibaba.mos.data.ChannelInventoryDO;
import com.alibaba.mos.data.SkuDO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;
import org.apache.commons.codec.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author superchao
 * @version $Id: SkuSimpleReadServiceImpl.java, v 0.1 2019年10月28日 11:54 AM superchao Exp $
 */
@Service
public class SkuSimpleReadServiceImpl implements SkuSimpleReadService {
    private static final Properties PROP = new Properties();

    static {
        try {
            PROP.load(SkuSimpleReadServiceImpl.class.getClassLoader().getResourceAsStream(
                    "application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SkuDO> loadSkus() {
        // 在此实现从resource文件excel中加载sku的代码
        final String xls = PROP.getProperty("data.xls");
        final List<SkuDO> list = new ArrayList<>();
        final Map<String, Integer> map = new HashMap<>();
        try (final InputStream is = Resources.getInputStream(xls)
                /*this.getClass().getClassLoader().getResourceAsStream(xls)*/
        ) {
            final Workbook workbook =
                    WorkbookParser.getWorkbook(is);
            final Sheet sheets = workbook.getSheet(0);
            final int columns = sheets.getColumns();
            final int rows = sheets.getRows();
            for (int j = 0; j < columns; j++) {
                final Cell first = sheets.getCell(j, 0);
                final String contents = first.getContents();
                map.put(contents, j);
            }
            for (int i = 1; i < rows; i++) {
                try {
                    final SkuDO skuDO = new SkuDO();
                    skuDO.setId(sheets.getCell(map.get("id"), i).getContents());
                    skuDO.setName(sheets.getCell(map.get("name"), i).getContents());
                    skuDO.setArtNo(sheets.getCell(map.get("artNo"), i).getContents());
                    skuDO.setSpuId(sheets.getCell(map.get("spuId"), i).getContents());
                    skuDO.setSkuType(sheets.getCell(map.get("skuType"), i).getContents());
                    skuDO.setPrice(new BigDecimal(sheets.getCell(map.get("price"), i).getContents()));
                    final String inventorys =
                            sheets.getCell(map.get("inventorys"), i).getContents();
                    final JSONArray jsonArray = (JSONArray) JSONObject.parse(inventorys);
                    final int size = jsonArray.size();
                    List<ChannelInventoryDO> invList = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        invList.add(jsonArray.getObject(j, ChannelInventoryDO.class));
                    }
                    skuDO.setInventoryList(invList);
                    list.add(skuDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return null;
    }
}