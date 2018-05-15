package com.example.demo.state.action.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.state.action.AbstractHonorAction;
import com.example.demo.state.action.ToRemoteAction;
import com.example.demo.state.support.HonorActionSupport;
import com.example.demo.vo.HonorDataHttpParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Slf4j
@Component
public class ParamsCheckAction extends AbstractHonorAction implements ToRemoteAction{
    @Override
    public void execute(StateContext context) throws Exception{
        UUID uuid = context.getStateMachine().getUuid();

        log.info("Params check:UUID:{}",getSimpleUUID(uuid));
        HonorDataHttpParam param = honorActionSupport.getStateMachineSupport().getParamByUUID(getSimpleUUID(uuid));
        //有效性检查
        //合法性检查
        log.info("Params check:UUID:{},params:{}",getSimpleUUID(uuid), JSONObject.toJSONString(param));
    }

}
