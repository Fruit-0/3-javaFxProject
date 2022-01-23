package com.yc.education.service.impl.basic;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.basic.DepotBasicMapper;
import com.yc.education.model.basic.DepotBasic;
import com.yc.education.service.basic.DepotBasicService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DepotBasicServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/4 15:31
 * @Version 1.0
 */

@Service("DepotBasicService")
public class DepotBasicServiceImpl extends BaseService<DepotBasic> implements DepotBasicService {

    @Autowired
    private DepotBasicMapper depotBasicMapper;


    @Override
    public String selectMaxIdnum() {
        return depotBasicMapper.selectMaxIdnum();
    }

    @Override
    public List<DepotBasic> selectDepotBasic(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return depotBasicMapper.selectDepotBasic();
    }

    @Override
    public List<DepotBasic> selectDepotBasic() {
        return depotBasicMapper.selectDepotBasic();
    }

    @Override
    public DepotBasic selectDepotBasicByIsnum(String idnum) {
        return depotBasicMapper.selectDepotBasicByIsnum(idnum);
    }

    @Override
    public List<DepotBasic> selectDepotBasicByParentId(long parentid) {
        return depotBasicMapper.selectDepotBasicByParentId(parentid);
    }

    @Override
    public DepotBasic selectDepotBasicByDepName(String depname) {
        return depotBasicMapper.selectDepotBasicByDepName(depname);
    }
}
