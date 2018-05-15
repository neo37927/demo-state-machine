package com.example.demo.state.action.impl;

import com.example.demo.state.action.AbstractHonorAction;
import com.example.demo.state.action.TaskHistoryAction;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 历史记录
 * <p>
 * Created by xiaolin on 2018/5/15.
 */
@Component
public class LocalHistoryAction extends AbstractHonorAction implements TaskHistoryAction {
    @Override
    public void execute(StateContext var1) throws Exception {
        Random r = new Random();
        if (r.nextBoolean()) {
            throw new Exception("Failed！");
        }
    }
}
