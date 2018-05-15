package com.example.demo.state.action;

import com.example.demo.state.support.HonorActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * 任务执行支持抽象类
 *
 * Created by xiaolin on 2018/5/15.
 */
public abstract class AbstractHonorAction {

    @Autowired
    protected HonorActionSupport honorActionSupport;

    protected String getSimpleUUID(UUID uuid){
        return honorActionSupport.getStateMachineSupport().getSimpleUUID(uuid);
    }
}
