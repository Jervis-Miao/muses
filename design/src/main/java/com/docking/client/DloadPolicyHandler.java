/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client;

import java.util.Map;

import com.docking.client.dto.req.AppPlanDTO;
import com.docking.client.dto.req.PolicyDTO;
import com.docking.client.dto.res.DloadPolicyData;
import com.docking.client.dto.res.UnderWritingData;

import cn.xyz.chaos.mvc.web.api.BaseResponseDTO;

/**
 * @author miaoqiang
 * @date 2019/6/10.
 */
public class DloadPolicyHandler extends
    AbstractHandler<DloadPolicyData, PolicyDTO, BaseResponseDTO<Map<String, DloadPolicyData>>> {
    @Override
    protected PolicyDTO initReqData(Long appId) {
        return null;
    }

    @Override
    protected DloadPolicyData formatRet(BaseResponseDTO<Map<String, DloadPolicyData>> result) {
        Map<String, DloadPolicyData> data = result.getData();
        return null;
    }
}
