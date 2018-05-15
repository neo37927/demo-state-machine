package com.example.demo.state;

import com.example.demo.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Map;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class HonorStateDriveConfigurer extends EnumStateMachineConfigurerAdapter<Constants.HonorStates, Constants.HonorEvents> {
    @Override
    public void configure(StateMachineStateConfigurer<Constants.HonorStates, Constants.HonorEvents> states) throws Exception {
        states
                .withStates()//主流程-工作
                .initial(Constants.HonorStates.READY)
                .end(Constants.HonorStates.CLOSED)
                .state(Constants.HonorStates.READY, (context) -> driveEvent(Constants.HonorStates.READY, context))
                .state(Constants.HonorStates.CLOSED, (context) -> driveEvent(Constants.HonorStates.CLOSED, context))
                .state(Constants.HonorStates.REMOTE, (context) -> driveEvent(Constants.HonorStates.REMOTE, context))
                .choice(Constants.HonorStates.REMOTE)
                .state(Constants.HonorStates.REMOTE_HISTORY, (context) -> driveEvent(Constants.HonorStates.REMOTE_HISTORY, context))
                .state(Constants.HonorStates.REMOTE_CHOICE, (context) -> driveEvent(Constants.HonorStates.REMOTE_CHOICE, context))
                .state(Constants.HonorStates.REMOTE_FORK, (context) -> driveEvent(Constants.HonorStates.REMOTE_FORK, context))
                .state(Constants.HonorStates.REMOTE_END, (context) -> driveEvent(Constants.HonorStates.REMOTE_END, context))
                .state(Constants.HonorStates.LOCAL, (context) -> driveEvent(Constants.HonorStates.LOCAL, context))
                .state(Constants.HonorStates.LOCAL_MONITOR, (context) -> driveEvent(Constants.HonorStates.LOCAL_MONITOR, context))
                .state(Constants.HonorStates.LOCAL_EXECUTER, (context) -> driveEvent(Constants.HonorStates.LOCAL_EXECUTER, context));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<Constants.HonorStates, Constants.HonorEvents> config) throws Exception {
//        super.configure(config);
        config.withConfiguration()
                .machineId("base-machine")
                .taskExecutor(new SyncTaskExecutor());
        //TODO 整合配置
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Constants.HonorStates, Constants.HonorEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Constants.HonorStates.READY).target(Constants.HonorStates.REMOTE)
                .event(Constants.HonorEvents.TO_REMOTE)
                .action((context) -> executeAction(Constants.HonorEvents.TO_REMOTE,context))
                .and()
                .withChoice()
                .source(Constants.HonorStates.REMOTE)
                .first(Constants.HonorStates.REMOTE_HISTORY, ((context) -> {
                    String type = String.valueOf(context.getMessageHeader("type"));
                    return "1".equals(type);
                }))
                .then(Constants.HonorStates.REMOTE_FORK, ((context) -> {
                    String type = String.valueOf(context.getMessageHeader("type"));
                    return "2".equals(type);
                }))
                .last(Constants.HonorStates.REMOTE_CHOICE, (context) -> {
                    log.info("State:{}", Constants.HonorStates.REMOTE_CHOICE.name());
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_HISTORY).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_HISTORY)
                .action((context) -> executeAction(Constants.HonorEvents.TASK_HISTORY,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_FORK).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_FORK)
                .action((context) -> executeAction(Constants.HonorEvents.TASK_FORK,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_CHOICE).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_CHOICE)
                .action((context) -> executeAction(Constants.HonorEvents.TASK_CHOICE,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_END).target(Constants.HonorStates.LOCAL)
                .event(Constants.HonorEvents.TO_LOCAL)
                .action((context) -> executeAction(Constants.HonorEvents.TO_LOCAL,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL).target(Constants.HonorStates.LOCAL_MONITOR)
                .event(Constants.HonorEvents.TASK_MONITOR)
                .action((context) -> executeAction(Constants.HonorEvents.TASK_MONITOR,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL_MONITOR).target(Constants.HonorStates.LOCAL_EXECUTER)
                .event(Constants.HonorEvents.TASK_EXECUTE)
                .action((context) -> executeAction(Constants.HonorEvents.TASK_EXECUTE,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL_EXECUTER).target(Constants.HonorStates.CLOSED)
                .event(Constants.HonorEvents.TO_CLOSED)
                .action((context) -> executeAction(Constants.HonorEvents.TO_CLOSED,context))
                .and()
                .withExternal()
                .source(Constants.HonorStates.CLOSED).target(Constants.HonorStates.READY)
                .event(Constants.HonorEvents.TO_READY)
                .action((context) -> executeAction(Constants.HonorEvents.TO_READY,context));
    }



    private void driveEvent(Constants.HonorStates state,
                            StateContext<Constants.HonorStates, Constants.HonorEvents> context) {
        if (state == null || state.getEvent() == null) return;
        //状态驱动事件
        context.getStateMachine().sendEvent(state.getEvent());
        /*MessageBuilder
                .withPayload(state.getEvent())
                .setHeader("foo", "bar")
                .build());*/
    }

    private void executeAction(Constants.HonorEvents event,StateContext<Constants.HonorStates, Constants.HonorEvents> context){
        if (event == null || event.getaClass() == null) {
            return;
        }
        log.info("UUID:{},Event:{}", context.getStateMachine().getUuid(),event.name());
        Class clazz = event.getaClass();
        Map<String,Action> actionMap = SpringContextHolder.getApplicationContext().getBeansOfType(clazz);
        for (String key : actionMap.keySet()){
            Action action = actionMap.get(key);
            action.execute(context);
        }

    }
}
