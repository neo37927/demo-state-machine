package com.example.demo.state.support;

import com.example.demo.state.Constants;
import com.example.demo.state.listener.HonorStateMachineListenerAdapterSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HonorStateMachineSupport {

    @Autowired
    private StateMachineFactory<Constants.HonorStates, Constants.HonorEvents> factory;

    @Autowired
    private HonorStateMachineListenerAdapterSupport listener;

    /* 状态机ID */
    private String machineId;//如何应用？

    /* UUID */
    private UUID UUID;

    public void start(String machineId, UUID UUID, String params) throws Exception {
        //构建状态机
        StateMachine<Constants.HonorStates, Constants.HonorEvents> machine = getMachine(UUID, machineId);
        listener.setMachine(machine);
        machine.addStateListener(listener);
        machine.start();
        machine.sendEvent(MessageBuilder
                .withPayload(Constants.HonorEvents.TO_REMOTE)
                .setHeader("params",params)
                .build());
    }

    /**
     * 构建状态机
     *
     * @return 状态机实例
     * @throws Exception 获取状态机失败
     */
    StateMachine<Constants.HonorStates, Constants.HonorEvents> getMachine(UUID UUID, String machineId) throws Exception {
        //TODO 状态源池
        return getFactury(machineId).getStateMachine(UUID, machineId);
    }

    /**
     * 获取状态机工厂
     *
     * @return 工厂实例
     * @throws Exception 获取状态机工厂失败
     */
    ObjectStateMachineFactory<Constants.HonorStates, Constants.HonorEvents> getFactury(String machineId) throws Exception {
        //TODO 工厂定制化
        if (factory instanceof ObjectStateMachineFactory) {
            return (ObjectStateMachineFactory) factory;
        }
        throw new RuntimeException("ObjectStateMachineFactory error");
    }

}
