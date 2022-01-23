package com.yc.education.service.impl.sale;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.sale.SaleQuotationMapper;
import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.sale.ISaleQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BlueSky
 * @Date: 2018/9/26 15:24
 */
@Service
public class SaleQuotationServiceImpl extends BaseService<SaleQuotation> implements ISaleQuotationService {

    @Autowired
    private SaleQuotationMapper mapper;

    @Override
    public List<SaleQuotation> listSaleQuotationAll(int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listSaleQuotationAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleQuotation getSaleQuotation(String offerno) {
        try {
            return  mapper.getSaleQuotation(offerno);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleQuotation getLastSaleQuotation() {
        try {
            return  mapper.getLastSaleQuotation();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleQuotation getFirstSaleQuotation() {
        try {
            return  mapper.getFirstSaleQuotation();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleQuotation getSaleQuotationByPage(int page, int rows) {
        try {
            return  mapper.getSaleQuotationByPage(page,rows);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getSaleQuotationCount() {
        try {
            return  mapper.getSaleQuotationCount();
        }catch (Exception e){
            return 0;
        }
    }
}
