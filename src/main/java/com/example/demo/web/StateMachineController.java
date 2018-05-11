package com.example.demo.web;

import com.example.demo.state.listener.HonorStateMachineListenerAdapterSupport;
import com.example.demo.state.Constants;
import com.example.demo.state.TaskStateMachineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RequestMapping("/set")
@RestController
public class StateMachineController {
    @Autowired
    private StateMachineFactory factory;

    @Autowired
    private HonorStateMachineListenerAdapterSupport stateMachineListenerAdapter;

    @RequestMapping("/state")
    @ResponseBody
    public String stateTest(@RequestParam(required = false,defaultValue = "TO_READY") String event) {

        UUID uuid = UUID.randomUUID();
        Arrays.stream(Constants.HonorEvents.values()).filter(value -> value.name().contains(event)).findFirst().ifPresent((v) -> {

            log.info("UUID:{}",uuid);
            StateMachine<Constants.HonorStates, Constants.HonorEvents> sm ;
            if (factory instanceof ObjectStateMachineFactory){
                sm = ((ObjectStateMachineFactory)factory).getStateMachine(uuid,"base-machine");

            }else{
                sm = factory.getStateMachine(uuid);
            }
            sm.addStateListener(stateMachineListenerAdapter);
            log.info("Thread-ID:{}",Thread.currentThread().getId());
            InheritableThreadLocal<StateMachine<Constants.HonorStates, Constants.HonorEvents>> local = new InheritableThreadLocal();
            local.set(sm);
            sm.start();
            Constants.HonorStates oldState = null;
            while (!sm.isComplete()){
                if (oldState == null){
                    oldState = sm.getState().getId();sm.sendEvent(sm.getState().getId().getEvent());continue;
                }
                if (!oldState.equals(sm.getState().getId())){
                    sm.sendEvent(sm.getState().getId().getEvent());
                }

            }

            log.info(sm.getState().toString());
        });

        return "OK";
    }
    @RequestMapping("/task")
    @ResponseBody
    public String taskState(@RequestParam(required = false,defaultValue = "RUN") String event) {

        UUID uuid = UUID.randomUUID();
        Arrays.stream(TaskStateMachineFactory.Events.values()).filter(value -> value.name().contains(event)).findFirst().ifPresent((v) -> {

            log.info("UUID:{}",uuid);
            StateMachine<TaskStateMachineFactory.States, TaskStateMachineFactory.Events> sm ;
            if (factory instanceof ObjectStateMachineFactory){
                sm = ((ObjectStateMachineFactory)factory).getStateMachine(uuid,"base-machine");

            }else{
                sm = factory.getStateMachine(uuid);
            }

            sm.start();
            sm.sendEvent(v);
        });

        return "OK";
    }
}
