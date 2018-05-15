package com.example.demo.state;

import com.example.demo.state.action.*;

public interface Constants {

    /**
     * 征信事件
     */
    enum HonorEvents {
        TO_CLOSED(null),
        TO_READY(null),
        TO_LOCAL(ToLocalAction.class),
        TO_REMOTE(ToRemoteAction.class),
        TASK_HISTORY(TaskHistoryAction.class),
        TASK_FORK(TaskForkAction.class),
        TASK_CHOICE(TaskChoiceAction.class),
        TASK_MONITOR(TaskMonitorAction.class),
        TASK_EXECUTE(TaskExecuteAction.class);

        private Class aClass;

        HonorEvents(Class aClass) {
            this.aClass = aClass;
        }

        public Class getaClass() {
            return aClass;
        }
    }

    /**
     * 征信状态
     */
    enum HonorStates {
        //工作状态
        REMOTE(null),//手动触发任务
        REMOTE_END(HonorEvents.TO_LOCAL),
        REMOTE_HISTORY(HonorEvents.TASK_HISTORY),//远程缓存策略
        REMOTE_CHOICE(HonorEvents.TASK_CHOICE),//远程权重策略
        REMOTE_FORK(HonorEvents.TASK_FORK),//远程遍历策略

        LOCAL(HonorEvents.TASK_MONITOR),
        LOCAL_MONITOR(HonorEvents.TASK_EXECUTE),//本地监听器
        LOCAL_EXECUTER(HonorEvents.TO_CLOSED),//本地执行器

        //结束状态
        CLOSED(null),

        //准备状态
        READY(null);

        private HonorEvents event;

        HonorStates(HonorEvents event) {
            this.event = event;
        }

        public HonorEvents getEvent() {
            return event;
        }
    }

}
