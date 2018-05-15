package com.example.demo.web;

import com.example.demo.state.TaskStateMachineConfigurer;
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

    @RequestMapping("/task")
    @ResponseBody
    public String taskState(@RequestParam(required = false,defaultValue = "RUN") String event) {

        UUID uuid = UUID.randomUUID();
        Arrays.stream(TaskStateMachineConfigurer.Events.values()).filter(value -> value.name().contains(event)).findFirst().ifPresent((v) -> {

            log.info("UUID:{}",uuid);
            StateMachine<TaskStateMachineConfigurer.States, TaskStateMachineConfigurer.Events> sm ;
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
