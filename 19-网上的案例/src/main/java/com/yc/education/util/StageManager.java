package com.yc.education.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.education.model.purchase.InquiryOrder;
import com.yc.education.model.purchase.InquiryProduct;
import com.yc.education.model.purchase.PurchaseOrders;
import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.model.sale.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author BlueSky
 * @Date 11:47 2018-08-21
 */
public class StageManager {
    /**
     *
     * @auther: BlueSky
     */
    public static Map<String, Stage> STAGE = new HashMap<String, Stage>();

    public static Map<String, Object> CONTROLLER = new HashMap<String, Object>();

    public static Map<String,Pane> ORDERCONTENT = new HashMap<String, Pane>();
    //询价单导出存入
    public static InquiryOrder inquiryOrderInfo = new InquiryOrder();
    //询价单导出导出询价产品
    public static List<InquiryProduct> inquiryProductsInfo = new ArrayList<>();
    //采购订单
    public static PurchaseOrders purchaseOrders = new PurchaseOrders();
    //采购订单产品导出
    public static List<PurchaseProduct> purchaseProducts = new ArrayList<>();
    //销售-报价单
    public static SaleQuotation saleQuotation = null;
    //销售-网上订单
    public static SaleOnlineOrder saleOnlineOrder = null;
    //销售-订货单
    public static SalePurchaseOrder salePurchaseOrder = null;
    //销售-销货单
    public static SaleGoods saleGoods = null;
    //销售-销货退货单
    public static SaleReturnGoods saleReturnGoods = null;
    // 集合
    public static List<?> PRODUCT_LIST = new ArrayList<>();


    /**
     * 清空所有集合和对象
     */
    public  static void clear(){

        StageManager.inquiryOrderInfo =  new InquiryOrder();

        StageManager.inquiryProductsInfo = new ArrayList<>();

        StageManager.purchaseOrders =  new PurchaseOrders();

        StageManager.purchaseProducts = new ArrayList<>();
    }


}