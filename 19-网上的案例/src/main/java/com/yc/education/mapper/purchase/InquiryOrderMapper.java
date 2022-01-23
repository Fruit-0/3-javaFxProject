package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.InquiryOrder;
import com.yc.education.util.MyMapper;

import java.util.List;

public interface InquiryOrderMapper extends MyMapper<InquiryOrder> {


    /**
     * 查询最大编号
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询询价单
    List<InquiryOrder> findInquiryOrder();



}