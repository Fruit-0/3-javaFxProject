package com.yc.education.service.impl.customer;


import com.yc.education.mapper.customer.InvoiceMapper;
import com.yc.education.model.customer.CustomerContacts;
import com.yc.education.model.customer.Invoice;
import com.yc.education.service.customer.IInvoiceService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 10:33 2018-08-24
 */
@Service
public class InvoiceServiceImpl extends BaseService<Invoice> implements IInvoiceService {

    @Autowired
    InvoiceMapper mapper;


    @Override
    public List<Invoice> listInvoice() {
        try {
            return mapper.listInvoice();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Invoice> listInvoiceByCustomerId(long customerid) {
        try {
            return mapper.listInvoiceByCustomerId(customerid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteInvoiceByCustomerId(long customerid) {
        try {
            return mapper.deleteInvoiceByCustomerId(customerid);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
