package com.fruit.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.lang.reflect.Method;

/**
 * @version 1.0.0
 * @项目名称：
 * @类名称：FruitJavaFxUtils
 * @类描述：存储了一些常用的javaFxde的构件的操作
 * @功能描述：存储了一些常用的javaFxde的构件的操作，进行了初步的抽象，方便后续调用
 * @author：FRUIT
 * @创建时间：2022/1/24 21:35
 * @修改备注 目前方法较少，目标是一周更新2-3个方法（多了就没时间干其他的了）
 * @修改记录： 修改时间 修改人员 修改原因
 * --------------------------------------------------
 */
public class FruitJavaFxUtils {

    /** 基本弹窗
     * @param alertType:     弹窗类型（NONE-无，INFORMATION-普通提示，WARNING-警告，CONFIRMATION-确认，ERROR-错误）
     * @param title:         弹窗抬头
     * @param alertInfo:     弹窗信息
     * @param alertInfoDesc: 弹窗信息详解
     * @param isSimple:      是否是普通弹窗（普通弹窗只有一个OK按钮，选择false时候，剩余的对象，方法，参数列表，不得为空，否则报错）
     * @param o:             确认后要执行的某个对象的某个方法（这个参数是对象）
     * @param methodName:    确认后要执行的某个对象的某个方法（这个参数是方法名）
     * @param param:         确认后要执行的某个对象的某个方法（这个参数是参数列表）
     * @Description: 通用弹窗（会阻塞线程），指定了基本的弹窗类型，弹窗的基本参数，以及选择确认按钮以后要执行的方法
     * @Author: YX-WJ
     * @Date: 2022/1/24 22:33
     * @return: void
     **/
    public static void alertFunction(Alert.AlertType alertType, String title, String alertInfo, String alertInfoDesc, Boolean isSimple, Object o, String methodName, Object... param) throws Exception {
        Alert alert = new Alert(alertType);
        //弹窗内容（执行失败！）
        alert.setHeaderText(alertInfo);
        //内容说明（失败原因是XXXXXXXXXX）
        alert.setContentText(alertInfoDesc);
        //弹窗标题
        alert.setTitle(title);
        if (!isSimple) {
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            if (o == null || methodName.isEmpty() || param.length == 0) {
                throw new Exception("当【isSimple】为true时，o，methodName，param均不得为空");
            }

        } else {
            // alert窗口默认的按钮只有一个 ButtonType.OK，是不会触发 response == ButtonType.YES 里的逻辑的
        }

        //弹出弹窗，并阻塞程序，直到点击确认，再继续执行
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                //点击确认以后执行的内容
                try {
                    Class<?> clazz = Class.forName(o.getClass().getName());
                    // 创建对象
                    Object instance = clazz.getConstructor().newInstance();
                    // 调用方法
                    for (Method method : clazz.getMethods()) {
                        if (methodName.equals(method.getName())) {
                            method.invoke(instance, param);//
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("不执行方法");
            }
        });
    }
}
