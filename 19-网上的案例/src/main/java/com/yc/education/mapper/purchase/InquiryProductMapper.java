package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.InquiryProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InquiryProductMapper extends MyMapper<InquiryProduct> {

     //根据询价单查询询价单产品
    List<InquiryProduct> findInquiryProductByInquiryid(@Param("inquiryid")long inquiryid);




}