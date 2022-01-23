package com.yc.education.service.impl.sale;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.sale.SaleGoodsMapper;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.sale.ISaleGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BlueSky
 * @Date: 2018/9/26 15:24
 */
@Service
public class SaleGoodsServiceImpl extends BaseService<SaleGoods> implements ISaleGoodsService {

    @Autowired
    private SaleGoodsMapper mapper;

    @Override
    public List<SaleGoods> listSaleGoodsByStates(String customerno, String begintime, String endtime, String verifystate, String deliverystate, String financialstate, String backstate) {
        try {
            return  mapper.listSaleGoodsByStates(customerno, begintime, endtime, verifystate, deliverystate, financialstate, backstate) ;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleGoods> listSaleGoodsAll() {
        try {
            return  mapper.listSaleGoodsAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleGoods> listSaleGoodsByPage(int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listSaleGoodsAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleGoods getSaleGoods(String orderno) {
        try {
            return  mapper.getSaleGoods(orderno);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleGoods getLastSaleGoods() {
        try {
            return  mapper.getLastSaleGoods();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleGoods getFirstSaleGoods() {
        try {
            return  mapper.getFirstSaleGoods();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleGoods getSaleGoodsByPage(int page, int rows) {
        try {
            return  mapper.getSaleGoodsByPage(page,rows);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getSaleGoodsCount() {
        try {
            return  mapper.selectCount(new SaleGoods());
        }catch (Exception e){
            return 0;
        }
    }



}
