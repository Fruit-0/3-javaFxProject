package com.yc.education.service.purchase;

import com.yc.education.model.purchase.InquiryProduct;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName InquiryProductService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/28 11:53
 * @Version 1.0
 */
public interface InquiryProductService extends IService<InquiryProduct> {

    //根据询价单查询询价单产品
    List<InquiryProduct> findInquiryProductByInquiryid(long inquiryid);


}
