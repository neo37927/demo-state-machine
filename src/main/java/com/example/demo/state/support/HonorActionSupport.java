package com.example.demo.state.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务执行支持类
 *
 * Created by xiaolin on 2018/5/15.
 */
@Component
public class HonorActionSupport {
    protected HonorStateMachineSupport stateMachineSupport;

    public HonorActionSupport(HonorStateMachineSupport stateMachineSupport) {
        this.stateMachineSupport = stateMachineSupport;
    }

    public HonorStateMachineSupport getStateMachineSupport() {
        return stateMachineSupport;
    }
}
