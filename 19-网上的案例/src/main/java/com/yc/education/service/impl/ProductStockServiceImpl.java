package com.yc.education.service.impl;

import com.yc.education.mapper.ProductStockMapper;
import com.yc.education.model.ProductStock;
import com.yc.education.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProducctStockServiceImpl
 * @Description TODO  产品库存
 * @Author QuZhangJing
 * @Date 2018/10/22 18:00
 * @Version 1.0
 */
@Service("ProductStockService")
public class ProductStockServiceImpl extends BaseService<ProductStock> implements ProductStockService {


    @Autowired
    private ProductStockMapper productStockMapper;


    @Override
    public List<ProductStock> findProductStock(String sisnum, String eisnum, String sproname, String eproname, String stype, String etype) {
        try {
            return productStockMapper.findProductStock(sisnum,eisnum,sproname,eproname,stype,etype);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProductStock findProductStockByProIsnum(String isnum) {
        try {
            return productStockMapper.findProductStockByProIsnum(isnum);
        } catch (Exception e) {
            return null;
        }
    }
}
