package com.yc.education.service.basic;

import com.yc.education.model.basic.ProductBasic;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName ProductBasicService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/5 10:01
 * @Version 1.0
 */
public interface ProductBasicService extends IService<ProductBasic> {



    /**
     * 查询最大编号
     * @return
     */
    String  selectMaxIdnum();


    /**
     * 查询所有产品
     * @return
     */
    List<ProductBasic> selectProductBasic(int pageNum,int pageSize);



    List<ProductBasic> selectProductBasic();


    /**
     * 根据编号查询产品
     * @param idnum
     * @return
     */
    ProductBasic selectProductBasicByIsnum(String idnum);


    /**
     * 产品价格设定搜索产品
     * @param isnum  产品编号
     * @param proname 产品名称
     * @param basicunit //基本单位
     * @param protype //产品大类
     * @return
     */
    List<ProductBasic> selectProductBasicSearch(String isnum,
                                                String proname,
                                                long basicunit,
                                               long protype);




}
