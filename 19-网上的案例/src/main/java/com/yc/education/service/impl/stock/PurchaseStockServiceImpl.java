package com.yc.education.service.impl.stock;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.stock.PurchaseStockMapper;
import com.yc.education.model.stock.PurchaseStock;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.stock.PurchaseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PurchaseStockServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/24 19:50
 * @Version 1.0
 */
@Service("PurchaseStockService")
public class PurchaseStockServiceImpl extends BaseService<PurchaseStock> implements PurchaseStockService {

    @Autowired
    private PurchaseStockMapper purchaseStockMapper;

    @Override
    public String selectMaxIdnum(String currentDate) {
        try {
            return purchaseStockMapper.selectMaxIdnum(currentDate);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PurchaseStock> findPurchaseStock() {
        try {
            return purchaseStockMapper.findPurchaseStock();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PurchaseStock> findPurchaseStock(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            return purchaseStockMapper.findPurchaseStock();
        } catch (Exception e) {
            return null;
        }
    }
}
