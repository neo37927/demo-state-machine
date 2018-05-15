package com.example.demo.state.action.impl;

import com.example.demo.state.action.ToRemoteAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParamsCheckAction implements ToRemoteAction{
    @Override
    public void execute(StateContext context) {
        log.info("Params check:UUID:{}",context.getStateMachine().getUuid());
    }
}
