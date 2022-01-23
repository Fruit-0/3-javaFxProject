package com.yc.education.service.impl.basic;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.basic.ProductBasicMapper;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductBasicServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/5 10:03
 * @Version 1.0
 */
@Service("ProductBasicService")
public class ProductBasicServiceImpl extends BaseService<ProductBasic> implements ProductBasicService {

    @Autowired
    private ProductBasicMapper productBasicMapper;


    @Override
    public String selectMaxIdnum() {
        return productBasicMapper.selectMaxIdnum();
    }

    @Override
    public List<ProductBasic> selectProductBasic(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            return productBasicMapper.selectProductBasic();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductBasic> selectProductBasic() {
        return productBasicMapper.selectProductBasic();
    }

    @Override
    public ProductBasic selectProductBasicByIsnum(String idnum) {
        return productBasicMapper.selectProductBasicByIsnum(idnum);
    }

    @Override
    public List<ProductBasic> selectProductBasicSearch(String isnum, String proname, long basicunit, long protype) {
        return productBasicMapper.selectProductBasicSearch(isnum,proname,basicunit,protype);
    }
}
