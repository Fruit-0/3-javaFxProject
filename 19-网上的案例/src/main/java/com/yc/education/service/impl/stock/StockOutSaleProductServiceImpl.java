package com.yc.education.service.impl.stock;

import com.yc.education.mapper.stock.StockOutSaleProductMapper;
import com.yc.education.model.stock.StockOutSaleProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.stock.IStockOutSaleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author BlueSky
 * @Date 2018-11-06 17:23
 */
@Service
public class StockOutSaleProductServiceImpl extends BaseService<StockOutSaleProduct> implements IStockOutSaleProductService {

    @Autowired
    private StockOutSaleProductMapper mapper;

    @Override
    public List<StockOutSaleProduct> listStockOutSaleProduct(String orderid) {
        try {
            return mapper.listStockOutSaleProduct(orderid);
        } catch (Exception e) {
            return null;
        }
    }
}
