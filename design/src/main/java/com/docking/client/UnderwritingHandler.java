/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client;

import cn.xyz.chaos.mvc.web.api.BaseResponseDTO;
import com.docking.client.dto.req.AppPlanDTO;
import com.docking.client.dto.res.UnderWritingData;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/6/10.
 */
public class UnderwritingHandler extends
    AbstractHandler<UnderWritingData, AppPlanDTO, BaseResponseDTO<Map<String, UnderWritingData>>> {

    @Override
    protected AppPlanDTO initReqData(Long appId) {
        return null;
    }

    @Override
    protected UnderWritingData formatRet(BaseResponseDTO<Map<String, UnderWritingData>> result) {
        Map<String, UnderWritingData> data = result.getData();
        return null;
    }
}