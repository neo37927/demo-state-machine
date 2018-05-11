package com.example.demo.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.model.StateMachineModelFactory;


@Slf4j
@Configuration
@EnableStateMachineFactory
public class HonorStateMachineFactory extends EnumStateMachineConfigurerAdapter<Constants.HonorStates, Constants.HonorEvents> {
    @Override
    public void configure(StateMachineConfigBuilder<Constants.HonorStates, Constants.HonorEvents> config) throws Exception {
        super.configure(config);
    }

    /**
     * 模型配置，支持拼接
     *
     * @param model
     * @throws Exception
     */
    @Override
    public void configure(StateMachineModelConfigurer<Constants.HonorStates, Constants.HonorEvents> model) throws Exception {
        super.configure(model);
//        model.withModel().factory(modelFactory());
    }

    /**
     * 基础配置
     *
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<Constants.HonorStates, Constants.HonorEvents> config) throws Exception {
//        super.configure(config);
        config.withConfiguration()
                .machineId("base-machine");
        //TODO 整合配置
    }

    /**
     * 状态配置
     *
     * @param states 状态
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<Constants.HonorStates, Constants.HonorEvents> states) throws Exception {
        states
                .withStates()//主流程-工作
                .initial(Constants.HonorStates.READY)
                .state(Constants.HonorStates.READY)
                .state(Constants.HonorStates.CLOSED)
                .choice(Constants.HonorStates.REMOTE)
                .state(Constants.HonorStates.REMOTE_HISTORY)
                .state(Constants.HonorStates.REMOTE_CHOICE)
                .state(Constants.HonorStates.REMOTE_FORK)
                .state(Constants.HonorStates.REMOTE_END)
                .state(Constants.HonorStates.LOCAL)
                .state(Constants.HonorStates.LOCAL_MONITOR)
                .state(Constants.HonorStates.LOCAL_EXECUTER);

    }

    /**
     * 事件流配置
     *
     * @param transitions 事件流
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<Constants.HonorStates, Constants.HonorEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Constants.HonorStates.READY).target(Constants.HonorStates.REMOTE)
                .event(Constants.HonorEvents.TO_REMOTE)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.REMOTE.name(),Constants.HonorEvents.TO_REMOTE.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withChoice()
                .source(Constants.HonorStates.REMOTE)
                .first(Constants.HonorStates.REMOTE_HISTORY, ((context) -> {
                    return Boolean.FALSE;//new Random().nextBoolean();
                }), (context) -> {
                    log.info("State:{}", Constants.HonorStates.REMOTE_HISTORY.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .then(Constants.HonorStates.REMOTE_FORK, ((context) -> {
                    return Boolean.FALSE;//new Random().nextBoolean();
                }), (context) -> {
                    log.info("State:{}", Constants.HonorStates.REMOTE_FORK.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .last(Constants.HonorStates.REMOTE_CHOICE, (context) -> {
                    log.info("State:{}", Constants.HonorStates.REMOTE_CHOICE.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_HISTORY).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_HISTORY)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.REMOTE_END.name(),Constants.HonorEvents.TASK_HISTORY.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_FORK).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_FORK)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.REMOTE_END.name(),Constants.HonorEvents.TASK_FORK.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_CHOICE).target(Constants.HonorStates.REMOTE_END)
                .event(Constants.HonorEvents.TASK_CHOICE)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.REMOTE_END.name(),Constants.HonorEvents.TASK_CHOICE.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.REMOTE_END).target(Constants.HonorStates.LOCAL)
                .event(Constants.HonorEvents.TO_LOCAL)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.REMOTE_END.name(),Constants.HonorEvents.TO_LOCAL.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL).target(Constants.HonorStates.LOCAL_MONITOR)
                .event(Constants.HonorEvents.TASK_MONITOR)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.LOCAL_MONITOR.name(),Constants.HonorEvents.TASK_MONITOR.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL_MONITOR).target(Constants.HonorStates.LOCAL_EXECUTER)
                .event(Constants.HonorEvents.TASK_EXECUTE)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.LOCAL_EXECUTER.name(),Constants.HonorEvents.TASK_EXECUTE.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.LOCAL_EXECUTER).target(Constants.HonorStates.CLOSED)
                .event(Constants.HonorEvents.TO_CLOSED)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.CLOSED.name(),Constants.HonorEvents.TO_CLOSED.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                })
                .and()
                .withExternal()
                .source(Constants.HonorStates.CLOSED).target(Constants.HonorStates.READY)
                .event(Constants.HonorEvents.TO_READY)
                .action((context) -> {
                    log.info("State:{},Event:{}", Constants.HonorStates.READY.name(),Constants.HonorEvents.TO_READY.name());
                    Object object = context.getMessageHeader("params");
                    log.info("Param:{}",String.valueOf(object));
                });
    }

    @Bean
    public StateMachineModelFactory<Constants.HonorStates, Constants.HonorEvents> modelFactory() {
        return new CustomStateMachineModelFactory();
    }
}
