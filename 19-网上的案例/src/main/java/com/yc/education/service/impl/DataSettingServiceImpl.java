package com.yc.education.service.impl;

import com.yc.education.mapper.DataSettingMapper;
import com.yc.education.model.DataSetting;
import com.yc.education.service.DataSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DataSettingServiceImpl
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/26 16:51
 * @Version 1.0
 */
@Service("DataSettingService")
public class DataSettingServiceImpl extends BaseService<DataSetting> implements DataSettingService {
    @Autowired
    private DataSettingMapper dataSettingMapper;

    @Override
    public List<DataSetting> findDataSetting(long parentid) {
        return dataSettingMapper.findDataSetting(parentid);
    }

    @Override
    public int updateDataSetting(DataSetting dataSetting) {
        return dataSettingMapper.updateDataSetting(dataSetting);
    }
}
