/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client;

/**
 * @author miaoqiang
 * @date 2019/6/10.
 */
public interface IUnderwHandler<T> {

    public T invoke(Long appId);
}
