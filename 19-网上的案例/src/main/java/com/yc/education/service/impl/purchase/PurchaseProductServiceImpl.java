package com.yc.education.service.impl.purchase;

import com.yc.education.mapper.purchase.PurchaseProductMapper;
import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.purchase.PurchaseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PurchaseProductServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/10 16:50
 * @Version 1.0
 */
@Service("PurchaseProducctService")
public class PurchaseProductServiceImpl extends BaseService<PurchaseProduct> implements PurchaseProductService {

    @Autowired
    private PurchaseProductMapper purchaseProductMapper;

    @Override
    public List<PurchaseProduct> findPurchaseProduct(long purchaseid) {
        try {
            return purchaseProductMapper.findPurchaseProduct(purchaseid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PurchaseProduct> findPurchaseProductNotStock() {
        try {
            return purchaseProductMapper.findPurchaseProductNotStock();
        } catch (Exception e) {
            return null;
        }
}

    @Override
    public PurchaseProduct findPurchaseProductByPronum(long  purchaseid, String pronum) {
        try {
            return purchaseProductMapper.findPurchaseProductByPronum(purchaseid,pronum);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PurchaseProduct> findPurchaseProductNew(String sisnum, String eisnum, String sproname, String eproname, String stype, String etype, String sdate, String edate) {
        try {
            return purchaseProductMapper.findPurchaseProductNew(sisnum,eisnum,sproname,eproname,stype,etype,sdate,edate);
        } catch (Exception e) {
            return null;
        }
    }
}
