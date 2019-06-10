/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client;

/**
 * @author miaoqiang
 * @date 2019/6/10.
 */
public abstract class AbstractHandler<T, D, R> implements IUnderwHandler<T> {

    @Override
    public T invoke(Long appId) {
        D reqData = this.initReqData(appId);
        R r = this.call(reqData);
        return this.formatRet(r);
    }

    protected abstract D initReqData(Long appId);

    protected R call(D reqData) {
        return null;
    }

    protected abstract T formatRet(R result);
}
