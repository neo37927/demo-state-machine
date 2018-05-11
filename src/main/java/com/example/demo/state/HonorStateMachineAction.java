package com.example.demo.state;

import org.springframework.statemachine.StateMachine;


public class HonorStateMachineAction {
    private StateMachine<Constants.HonorStates, Constants.HonorEvents> sm;

    public HonorStateMachineAction(StateMachine<Constants.HonorStates, Constants.HonorEvents> sm) {
        this.sm = sm;
    }


}
