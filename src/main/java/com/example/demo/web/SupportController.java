package com.example.demo.web;

import com.example.demo.state.support.HonorStateMachineSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("support")
public class SupportController {

    @Autowired
    private HonorStateMachineSupport support;

    @RequestMapping("state")
    public String setState(@RequestParam(required = false, defaultValue = "1") String type,
                           @RequestParam(required = false) Integer times) throws Exception {
        int i = (times == null|| times == 0) ? 1 : times;
        while (i > 0) {
            support.start("base-machine", UUID.randomUUID(), type);
            i--;
        }
        return "OK";
    }
}
