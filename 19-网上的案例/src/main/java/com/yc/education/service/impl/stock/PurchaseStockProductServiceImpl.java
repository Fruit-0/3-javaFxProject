package com.yc.education.service.impl.stock;

import com.yc.education.mapper.stock.PurchaseStockProductMapper;
import com.yc.education.model.stock.PurchaseStockProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.stock.PurchaseStockProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PurchaseStockProductServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/25 15:30
 * @Version 1.0
 */
@Service("PurchaseStockProduct")
public class PurchaseStockProductServiceImpl extends BaseService<PurchaseStockProduct> implements PurchaseStockProductService {

    @Autowired
    private PurchaseStockProductMapper purchaseStockProductMapper;


    @Override
    public List<PurchaseStockProduct> findStockProductBypurchaseStockId(long id) {
        try {
            return purchaseStockProductMapper.findStockProductBypurchaseStockId(id);
        } catch (Exception e) {
            return null;
        }
    }
}
