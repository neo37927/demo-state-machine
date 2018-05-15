package com.example.demo.state;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachineSystemConstants;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

//@Configuration
//@EnableStateMachineFactory
public class TaskStateMachineConfigurer extends EnumStateMachineConfigurerAdapter<TaskStateMachineConfigurer.States, TaskStateMachineConfigurer.Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .machineId("task");
    }

    //tag::snippetAA[]
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.READY)
                .fork(States.FORK)
                .state(States.TASKS)
                .join(States.JOIN)
                .choice(States.CHOICE)
                .state(States.ERROR)
                .and()
                .withStates()
                .parent(States.TASKS)
                .initial(States.T1)
                .end(States.T1E)
                .and()
                .withStates()
                .parent(States.TASKS)
                .initial(States.T2)
                .end(States.T2E)
                .and()
                .withStates()
                .parent(States.TASKS)
                .initial(States.T3)
                .end(States.T3E)
                .and()
                .withStates()
                .parent(States.ERROR)
                .initial(States.AUTOMATIC)
                .state(States.AUTOMATIC, automaticAction(), null)
                .state(States.MANUAL);
    }
//end::snippetAA[]

    //tag::snippetAB[]
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.READY).target(States.FORK)
                .event(Events.RUN)
                .and()
                .withFork()
                .source(States.FORK).target(States.TASKS)
                .and()
                .withExternal()
                .source(States.T1).target(States.T1E)
                .and()
                .withExternal()
                .source(States.T2).target(States.T2E)
                .and()
                .withExternal()
                .source(States.T3).target(States.T3E)
                .and()
                .withJoin()
                .source(States.TASKS).target(States.JOIN)
                .and()
                .withExternal()
                .source(States.JOIN).target(States.CHOICE)
                .and()
                .withChoice()
                .source(States.CHOICE)
                .first(States.ERROR, tasksChoiceGuard())
                .last(States.READY)
                .and()
                .withExternal()
                .source(States.ERROR).target(States.READY)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.AUTOMATIC).target(States.MANUAL)
                .event(Events.FALLBACK)
                .and()
                .withInternal()
                .source(States.MANUAL)
                .action(fixAction())
                .event(Events.FIX);
    }

    @Bean
    public Guard<States, Events> tasksChoiceGuard() {
        return new Guard<States, Events>() {

            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                return !(ObjectUtils.nullSafeEquals(variables.get("T1"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T2"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T3"), true));
            }
        };
    }
//end::snippetAC[]

    //tag::snippetAD[]
    @Bean
    public Action<States, Events> automaticAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                if (ObjectUtils.nullSafeEquals(variables.get("T1"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T2"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T3"), true)) {
                    context.getStateMachine().sendEvent(Events.CONTINUE);
                } else {
                    context.getStateMachine().sendEvent(Events.FALLBACK);
                }
            }
        };
    }

    @Bean
    public Action<States, Events> fixAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                variables.put("T1", true);
                variables.put("T2", true);
                variables.put("T3", true);
                context.getStateMachine().sendEvent(Events.CONTINUE);
            }
        };
    }
//end::snippetAD[]

    @Bean
    public Tasks tasks() {
        return new Tasks();
    }

    //tag::snippetAE[]
    @Bean(name = StateMachineSystemConstants.TASK_EXECUTOR_BEAN_NAME)
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        return taskExecutor;
    }

    public enum States {
        READY,
        FORK, JOIN, CHOICE,
        TASKS, T1, T1E, T2, T2E, T3, T3E,
        ERROR, AUTOMATIC, MANUAL
    }

    public enum Events {
        RUN, FALLBACK, CONTINUE, FIX;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @OnTransition
    public @interface StatesOnTransition {

        States[] source() default {};

        States[] target() default {};

    }
}



