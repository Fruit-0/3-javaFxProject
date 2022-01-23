package com.yc.education.mapper.customer;

import com.yc.education.model.customer.Industry;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IndustryMapper extends MyMapper<Industry> {

    /**
     * 查询所有的行业
     * @return
     */
    @Select("select * from industry order by sort desc , addtime desc")
    List<Industry> listIndustryAll();
}