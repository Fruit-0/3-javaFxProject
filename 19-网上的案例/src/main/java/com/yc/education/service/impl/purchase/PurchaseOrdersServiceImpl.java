package com.yc.education.service.impl.purchase;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.purchase.PurchaseOrdersMapper;
import com.yc.education.model.purchase.PurchaseOrders;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.purchase.PurchaseOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PurchaseOrdersServiceImpl
 * @Description TODO 采购订单
 * @Author QuZhangJing
 * @Date 2018/10/9 17:43
 * @Version 1.0
 */
@Service("PurchaseOrdersService")
public class PurchaseOrdersServiceImpl extends BaseService<PurchaseOrders> implements PurchaseOrdersService  {

    @Autowired
    private PurchaseOrdersMapper purchaseOrdersMapper;

    @Override
    public String selectMaxIdnum(String currentDate) {
        try {
            return purchaseOrdersMapper.selectMaxIdnum(currentDate);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PurchaseOrders> findPurchaseOrders() {
        try {
            return purchaseOrdersMapper.findPurchaseOrders();
        } catch (Exception e) {
            return  null;
        }
    }

    @Override
    public List<PurchaseOrders> findPurchaseOrders(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            return purchaseOrdersMapper.findPurchaseOrders();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PurchaseOrders findPurchaseByOrder(String orders) {
        try {
            return purchaseOrdersMapper.findPurchaseByOrder(orders);
        } catch (Exception e) {
            return null;
        }
    }
}
