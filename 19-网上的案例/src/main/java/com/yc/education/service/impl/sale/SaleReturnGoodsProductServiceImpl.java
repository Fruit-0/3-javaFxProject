package com.yc.education.service.impl.sale;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.sale.SalePurchaseOrderProductMapper;
import com.yc.education.mapper.sale.SaleReturnGoodsProductMapper;
import com.yc.education.model.sale.SalePurchaseOrderProduct;
import com.yc.education.model.sale.SaleReturnGoods;
import com.yc.education.model.sale.SaleReturnGoodsProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.sale.ISalePurchaseOrderProductService;
import com.yc.education.service.sale.ISaleReturnGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BlueSky
 * @Date: 2018/9/26 15:24
 */
@Service
public class SaleReturnGoodsProductServiceImpl extends BaseService<SaleReturnGoodsProduct> implements ISaleReturnGoodsProductService {

    @Autowired
    private SaleReturnGoodsProductMapper mapper;

    @Override
    public List<SaleReturnGoodsProduct> listReturnGoodsProduct(String orderid) {
        try {
            return  mapper.listSaleReturnGoodsProduct(orderid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleReturnGoodsProduct> listReturnGoodsProduct(String orderid, int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listSaleReturnGoodsProduct(orderid);
        }catch (Exception e){
            return null;
        }
    }

}
