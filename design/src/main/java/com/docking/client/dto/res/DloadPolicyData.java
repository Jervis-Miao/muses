/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client.dto.res;

/**
 * @author miaoqiang
 * @date 2019/5/31.
 */
public class DloadPolicyData extends BaseResData {

    /**
     * 文件ID
     */
    private Long fileId;

    public DloadPolicyData(BaseResData.RESULT_CODE resultCode, String retDesc) {
        super(resultCode, retDesc);
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
