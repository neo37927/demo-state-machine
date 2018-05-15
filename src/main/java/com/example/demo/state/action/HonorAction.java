package com.example.demo.state.action;

import org.springframework.statemachine.StateContext;

/**
 * Created by xiaolin on 2018/5/15.
 */
public interface HonorAction<S, E> {
    void execute(StateContext<S, E> var1) throws Exception;
}