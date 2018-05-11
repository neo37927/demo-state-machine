package com.example.demo.state;

import org.springframework.statemachine.config.model.*;

public class CustomStateMachineModelFactory implements StateMachineModelFactory<Constants.HonorStates, Constants.HonorEvents> {

    @Override
    public StateMachineModel<Constants.HonorStates, Constants.HonorEvents> build() {
        /*ConfigurationData<Constants.HonorStates, Constants.HonorEvents> configurationData = new ConfigurationData<>();
        Collection<StateData<Constants.HonorStates, Constants.HonorEvents>> stateData = new ArrayList<>();

        stateData.add(new StateData<>(Constants.HonorStates.IDLE, true));

        StatesData<Constants.HonorStates, Constants.HonorEvents> statesData = new StatesData<>(stateData);
        Collection<TransitionData<Constants.HonorStates, Constants.HonorEvents>> transitionData = new ArrayList<>();
        transitionData.add(new TransitionData<>(Constants.HonorStates.IDLE, Constants.HonorStates.READY, Constants.HonorEvents.TO_WORK));
        TransitionsData<Constants.HonorStates, Constants.HonorEvents> transitionsData = new TransitionsData<>(transitionData);
        StateMachineModel<Constants.HonorStates, Constants.HonorEvents> stateMachineModel = new DefaultStateMachineModel<>(configurationData,
                statesData, transitionsData);
        return stateMachineModel;*/
        return null;
    }

    @Override
    public StateMachineModel<Constants.HonorStates, Constants.HonorEvents> build(String machineId) {
        return build();
    }
}
