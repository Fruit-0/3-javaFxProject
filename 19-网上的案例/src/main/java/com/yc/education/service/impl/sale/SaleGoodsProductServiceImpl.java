package com.yc.education.service.impl.sale;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.sale.SaleGoodsProductMapper;
import com.yc.education.mapper.sale.SaleReturnGoodsProductMapper;
import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.model.sale.SaleReturnGoodsProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.sale.ISaleGoodsProductService;
import com.yc.education.service.sale.ISaleReturnGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BlueSky
 * @Date: 2018/9/26 15:24
 */
@Service
public class SaleGoodsProductServiceImpl extends BaseService<SaleGoodsProduct> implements ISaleGoodsProductService {

    @Autowired
    private SaleGoodsProductMapper mapper;


    @Override
    public List<SaleGoodsProduct> listSaleGoodsProduct(String orderid) {
        try {
            return  mapper.listSaleGoodsProduct(orderid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleGoodsProduct> listSaleGoodsProduct(String orderid, int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listSaleGoodsProduct(orderid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleGoodsProduct> listSaleGoodsProductDiscount(String customerNo, String customerNoEnd, String productNo, String productNoEnd, String productName, String productNameEnd, String saleDateStr, String saleDateEndStr, String customerName, String customerNameEnd, String discountStr) {
        try {
            return  mapper.listSaleGoodsProductDiscount(customerNo, customerNoEnd, productNo, productNoEnd, productName, productNameEnd, saleDateStr, saleDateEndStr, customerName, customerNameEnd, discountStr);
        }catch (Exception e){
            return null;
        }
    }
}
