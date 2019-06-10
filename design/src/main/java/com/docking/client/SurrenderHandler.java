/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client;

import java.util.Map;

import com.docking.client.dto.req.SurrenderDTO;
import com.docking.client.dto.res.BaseResData;

import cn.xyz.chaos.mvc.web.api.BaseResponseDTO;

/**
 * @author miaoqiang
 * @date 2019/6/10.
 */
public class SurrenderHandler extends
    AbstractHandler<BaseResData, SurrenderDTO, BaseResponseDTO<Map<String, BaseResData>>> {

    @Override
    protected SurrenderDTO initReqData(Long appId) {
        return null;
    }

    @Override
    protected BaseResData formatRet(BaseResponseDTO<Map<String, BaseResData>> result) {
        Map<String, BaseResData> data = result.getData();
        return null;
    }

}
