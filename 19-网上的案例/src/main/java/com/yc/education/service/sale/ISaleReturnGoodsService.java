package com.yc.education.service.sale;

import com.yc.education.model.sale.SaleReturnGoods;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleReturnGoodsService extends IService<SaleReturnGoods> {

    /**
     * 查询全部订单
     * @return
     */
    List<SaleReturnGoods> listSaleReturnGoodsAll();

    /**
     * 查询全部订单
     * @return
     */
    List<SaleReturnGoods> listSaleReturnGoodsByPage(int page, int rows);

    /**
     * 根据订货单号查询
     * @param orderno
     * @return
     */
    SaleReturnGoods getSaleReturnGoods(String orderno);

    /**
     * 最后一条数据
     * @return
     */
    SaleReturnGoods getLastSaleReturnGoods();

    /**
     * 第一条数据
     * @return
     */
    SaleReturnGoods getFirstSaleReturnGoods();

    /**
     * 获取上下页数据
     * @return
     */
    SaleReturnGoods getSaleReturnGoodsByPage(int page, int rows);

    /**
     * 统计订单数量
     * @return
     */
    int getSaleReturnGoodsCount();
}
