package com.yc.education.mapper.customer;

import com.yc.education.model.customer.Invoice;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InvoiceMapper extends MyMapper<Invoice> {

    /**
     * 查询所有
     * @return
     */
    List<Invoice> listInvoice();

    /**
     * 通过客户id查询发票
     * @return
     */
    List<Invoice> listInvoiceByCustomerId(@Param("customerid") long customerid);

    /**
     * 通过客户id删除发票
     * @param customerid
     * @return
     */
    int deleteInvoiceByCustomerId(@Param("customerid") long customerid);
}