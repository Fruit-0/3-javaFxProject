package com.yc.education.service.impl.basic;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.basic.CompanyBasicMapper;
import com.yc.education.model.basic.CompanyBasic;
import com.yc.education.service.basic.CompanyBasicService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CompanyBasicServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/8/30 16:17
 * @Version 1.0
 */
@Service("CompanyBasicService")
public class CompanyBasicServiceImpl extends BaseService<CompanyBasic> implements CompanyBasicService {

    @Autowired
    private CompanyBasicMapper companyBasicMapper;

    @Override
    public String selectMaxIdnum() {
        return companyBasicMapper.selectMaxIdnum();
    }

    @Override
    public List<CompanyBasic> selectCompanyBasic(int pageNum,int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            return companyBasicMapper.selectCompanyBasic();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<CompanyBasic> selectCompanyBasic() {
        return companyBasicMapper.selectCompanyBasic();
    }

    @Override
    public CompanyBasic selectCompanyBasicByIsnum(String idnum) {
        return companyBasicMapper.selectCompanyBasicByIsnum(idnum);
    }
}
