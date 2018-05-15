package com.example.demo.state.support;

import com.example.demo.state.Constants;
import com.example.demo.state.listener.HonorStateMachineListenerAdapterSupport;
import com.example.demo.vo.HonorData;
import com.example.demo.vo.HonorDataHttpParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
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

//    private HashMap<String,ObjectStateMachineFactory> StateMachinePool;
    private HashMap<String,HonorDataHttpParam> paramMap;
    private HashMap<String,HonorData> honorMap;

    public void start(String machineId, UUID UUID, String params) throws Exception {
        //构建状态机
        StateMachine<Constants.HonorStates, Constants.HonorEvents> machine = getMachine(UUID, machineId);
        listener.setMachine(machine);

        machine.addStateListener(listener);
        machine.start();
        machine.sendEvent(MessageBuilder
                .withPayload(Constants.HonorEvents.TO_REMOTE)
                .setHeader("type",params)
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
        long e = System.nanoTime();
        StateMachine<Constants.HonorStates, Constants.HonorEvents> stateMachine = getFactury(machineId).getStateMachine(UUID, machineId);
        log.info("创建StateMachine：用时：{}", TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - e));
        return stateMachine;
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

    public HonorDataHttpParam getParamByUUID(String uuid){
        return paramMap.get(uuid);
    }

    public HonorData getHonorByUUID(String uuid){
        return honorMap.get(uuid);
    }

}
