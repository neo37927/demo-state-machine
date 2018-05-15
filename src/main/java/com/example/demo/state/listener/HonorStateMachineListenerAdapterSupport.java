package com.example.demo.state.listener;

import com.example.demo.state.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


@Slf4j
@Component
public class HonorStateMachineListenerAdapterSupport extends StateMachineListenerAdapter<Constants.HonorStates, Constants.HonorEvents> {

    private StateMachine<Constants.HonorStates, Constants.HonorEvents> machine;

    @Override
    public void stateChanged(State<Constants.HonorStates, Constants.HonorEvents> from,
                             State<Constants.HonorStates, Constants.HonorEvents> to) {
       /* if (to == null || to.getId().equals(Constants.HonorStates.READY)) return;
        machine.sendEvent(MessageBuilder
                .withPayload(to.getId().getEvent())
                .setHeader("foo", "bar")
                .build());*/
    }

    @Override
    public void stateMachineStarted(StateMachine<Constants.HonorStates, Constants.HonorEvents> stateMachine) {
        log.info("StateMachineStarted:UUID:{},time:{}",stateMachine.getUuid(), LocalTime.now());
    }

    @Override
    public void stateMachineStopped(StateMachine<Constants.HonorStates, Constants.HonorEvents> stateMachine) {
        log.info("StateMachineEnd:UUID:{},time:{}",stateMachine.getUuid(), LocalTime.now());
    }

    public void setMachine(StateMachine<Constants.HonorStates, Constants.HonorEvents> machine) {
        this.machine = machine;
    }



}
