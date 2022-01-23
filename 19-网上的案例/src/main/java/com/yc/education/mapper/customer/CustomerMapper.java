package com.yc.education.mapper.customer;

import com.yc.education.model.customer.Customer;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper extends MyMapper<Customer> {

    /**
     * 查询现有客户（小窗口）
     * @return
     */
    List<Customer> listExistCustomer();

    /**
     * @Author BlueSky
     * @Description //TODO 查询所有客户
     * @Date 11:17 2018/8/20
     * @Param []
     * @return java.util.List<com.yc.education.model.customer.Customer>
     **/
    List<Customer> listCustomerAll();

    /**
     * @Author BlueSky
     * @Description //TODO 查询客户
     * @Date 11:40 2018/8/20
     * @Param []
     * @return com.yc.education.model.customer.Customer
     **/
    Customer getCustomer(@Param("code")String code);

    /**
     * 最后一条数据
     * @return
     */
    public Customer getLastCustomer();

    /**
     * 第一条数据
     * @return
     */
    Customer getFirstCustomer();

    /**
     * 获取上下页数据
     * @return
     */
    Customer getCustomerByPage(@Param("page")int page, @Param("rows")int rows);

    /**
     * 统计客户数量
     * @return
     */
    int getCustomerCount();
}