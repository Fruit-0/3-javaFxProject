package com.fruit.javafxtool;

import com.fruit.utils.FruitJavaFxUtils;

/**
 * @version 1.0.0
 * @项目名称：
 * @类名称：
 * @类描述：
 * @功能描述：
 * @author：FRUIT
 * @创建时间：2022/1/24 22:24
 * @修改备注
 * @修改记录： 修改时间 修改人员 修改原因
 * --------------------------------------------------
 */
public class t {
    public static void main(String[] args) throws ClassNotFoundException {
        FruitJavaFxUtils fruitJavaFxUtils = new FruitJavaFxUtils();
        String a = fruitJavaFxUtils.getClass().getName();
        System.out.println(a);
        Class<?> clazz = Class.forName(a);

        System.out.println(a);

    }

}
