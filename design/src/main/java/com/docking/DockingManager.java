/*
 * Copyright 2018 All rights reserved.
 */

package com.docking;

import com.docking.config.AnalysisConf;
import com.docking.config.ConfManager;
import com.docking.config.DataConf;
import com.docking.config.FollowConfig;
import com.docking.config.SendConf;
import com.docking.config.TaskConfig;
import com.docking.constant.ConfigCons;
import com.docking.dto.DockingReqDTO;
import com.docking.dto.DockingResDTO;

import cn.muses.utils.spring.SpringContextUtils;

/**
 * 对接执行管理
 *
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingManager {

    /**
     * 处理调度
     *
     * @param reqDTO
     * @return
     */
    public static DockingResDTO handleFollow(DockingReqDTO reqDTO) {
        FollowConfig config = new FollowConfig();
        DockingResDTO res = null;
        for (String code : config.getTaskCode()) {
            res = handleTask(code, reqDTO);
        }
        return res;
    }

    /**
     * 处理请求
     *
     * @param reqDTO
     * @return
     */
    public static DockingResDTO handleTask(String code, DockingReqDTO reqDTO) {
        TaskConfig config = ConfManager.get(code);
        Object reqMsg = getReqMsg(config.getDataConf(), reqDTO);
        Object resMsg = sendReqMsg(config.getCode(), config.getSendConf(), reqMsg, reqDTO);
        DockingResDTO dockingResDTO = analysisResMsg(config.getAnalysisConf(), resMsg);
        return dockingResDTO;
    }

    /**
     * 封装发送数据
     *
     * @param msgConf
     * @param reqDTO
     * @return
     */
    private static Object getReqMsg(DataConf dataConf, DockingReqDTO reqDTO) {
        String beanName = ConfigCons.ASSEMBLE_TYPE.getBeanNameByType(dataConf.getAssembleBeanType());
        IAssembleReqBody iAssemble = SpringContextUtils.getBean(beanName);
        return iAssemble.getReqBody(dataConf, reqDTO);
    }

    /**
     * 发送数据
     *
     * @param sendConf
     * @param reqMsg
     * @param reqDTO
     * @return
     */
    private static Object sendReqMsg(String code, SendConf sendConf, Object reqData, DockingReqDTO reqDTO) {
        ISendReqBody iSend = SpringContextUtils.getBean("");
        if (sendConf.getDownloadFlag()) {
            return iSend.sendForByte(code, sendConf, reqData, reqDTO);
        } else {
            return iSend.send(code, sendConf, reqData, reqDTO);
        }
    }

    /**
     * 解析返回信息
     *
     * @param analysisConf
     * @param resMsg
     * @return
     */
    private static DockingResDTO analysisResMsg(AnalysisConf analysisConf, Object resMsg) {
        IAnalysisResBody iAnalysis = SpringContextUtils.getBean("");
        return iAnalysis.analysis(analysisConf, resMsg);
    }
}
