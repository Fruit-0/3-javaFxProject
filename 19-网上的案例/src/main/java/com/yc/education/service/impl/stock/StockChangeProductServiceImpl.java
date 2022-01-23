package com.yc.education.service.impl.stock;

import com.yc.education.mapper.stock.StockChangeProductMapper;
import com.yc.education.model.stock.StockChangeProduct;
import com.yc.education.service.impl.BaseService;
import com.yc.education.service.stock.StockChangeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StockChangeProductServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/11/5 20:37
 * @Version 1.0
 */
@Service("StockChangeProductService")
public class StockChangeProductServiceImpl extends BaseService<StockChangeProduct> implements StockChangeProductService {

    @Autowired
    private StockChangeProductMapper stockChangeProductMapper;


    @Override
    public List<StockChangeProduct> findStockChangeProduct(long stockChangeId) {
        try{
            return stockChangeProductMapper.findStockChangeProduct(stockChangeId);
        }catch (Exception e){
            return null;
        }
    }
}
