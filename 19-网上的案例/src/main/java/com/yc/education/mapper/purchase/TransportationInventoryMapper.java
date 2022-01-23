package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.TransportationInventory;
import com.yc.education.util.MyMapper;

import java.util.List;

public interface TransportationInventoryMapper extends MyMapper<TransportationInventory> {


    //查询所有在途库存
    List<TransportationInventory> findTransportationInventory();



}