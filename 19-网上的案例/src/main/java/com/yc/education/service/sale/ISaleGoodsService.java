package com.yc.education.service.sale;

import com.yc.education.model.sale.SaleGoods;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleGoodsService extends IService<SaleGoods> {

    /**
     * 销货单状态更新
     * 差 财务复核状态、回传状态 条件
     * @param customerno 客户编号
     * @param begintime 创建时间-开始
     * @param endtime 创建时间-结束
     * @param verifystate 审核状态
     * @param deliverystate 发送状态
     * @param financialstate 财务复核状态
     * @param backstate 回传状态
     * @return
     */
    List<SaleGoods> listSaleGoodsByStates( String customerno, String begintime, String endtime, String verifystate, String deliverystate, String financialstate, String backstate);

    /**
     * 查询全部订单
     * @return
     */
    List<SaleGoods> listSaleGoodsAll();

    /**
     * 查询全部订单
     * @return
     */
    List<SaleGoods> listSaleGoodsByPage(int page, int rows);

    /**
     * 根据销售单号查询
     * @param orderno
     * @return
     */
    SaleGoods getSaleGoods(String orderno);

    /**
     * 最后一条数据
     * @return
     */
    SaleGoods getLastSaleGoods();

    /**
     * 第一条数据
     * @return
     */
    SaleGoods getFirstSaleGoods();

    /**
     * 获取上下页数据
     * @return
     */
    SaleGoods getSaleGoodsByPage(int page, int rows);

    /**
     * 统计订单数量
     * @return
     */
    int getSaleGoodsCount();
}
