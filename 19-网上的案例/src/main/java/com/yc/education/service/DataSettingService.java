package com.yc.education.service;

import com.yc.education.model.DataSetting;

import java.util.List;

/**
 * @ClassName DataSettingService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/9/26 16:50
 * @Version 1.0
 */
public interface DataSettingService extends IService<DataSetting> {

    //查询资料设定
    List<DataSetting> findDataSetting(long parentid);
    //根据排序号和父级id修改资料设定
    int updateDataSetting(DataSetting dataSetting);

}
