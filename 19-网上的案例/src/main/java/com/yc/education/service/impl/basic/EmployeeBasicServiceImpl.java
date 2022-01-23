package com.yc.education.service.impl.basic;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.basic.EmployeeBasicMapper;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EmployeeBasicServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/8/31 16:25
 * @Version 1.0
 */
@Service
public class EmployeeBasicServiceImpl extends BaseService<EmployeeBasic> implements EmployeeBasicService {

    @Autowired
    private EmployeeBasicMapper employeeBasicMapper;


    @Override
    public String selectMaxIdnum() {
        return employeeBasicMapper.selectMaxIdnum();
    }

    @Override
    public List<EmployeeBasic> selectEmployeeBasic(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            return employeeBasicMapper.selectEmployeeBasic();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmployeeBasic> selectEmployeeBasic() {
        return employeeBasicMapper.selectEmployeeBasic();
    }

    @Override
    public EmployeeBasic selectEmployeeBasicByIsnum(String idnum) {
        return employeeBasicMapper.selectEmployeeBasicByIsnum(idnum);
    }
}
