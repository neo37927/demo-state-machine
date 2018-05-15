package com.example.demo.state;

import org.springframework.statemachine.config.model.*;

public class CustomStateMachineModelFactory implements StateMachineModelFactory<Constants.HonorStates, Constants.HonorEvents> {

    @Override
    public StateMachineModel<Constants.HonorStates, Constants.HonorEvents> build() {
        return null;
    }

    @Override
    public StateMachineModel<Constants.HonorStates, Constants.HonorEvents> build(String machineId) {
        return build();
    }
}
