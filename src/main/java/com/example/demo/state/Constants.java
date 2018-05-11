package com.example.demo.state;

public interface Constants {

    /**
     * 征信事件
     */
    enum HonorEvents {
//        TO_IDLE,
        TO_CLOSED,
//        TO_WORK,
        TO_READY,
        TO_LOCAL,
        TO_REMOTE,
        TASK_HISTORY,
        TASK_FORK,
        TASK_CHOICE,
        TASK_MONITOR,
        TASK_EXECUTE;
    }

    /**
     * 征信状态
     */
    enum HonorStates {
        //工作状态
//        WORK,
        REMOTE(HonorEvents.TO_LOCAL),
        REMOTE_END(HonorEvents.TO_LOCAL),
        REMOTE_HISTORY(HonorEvents.TASK_HISTORY),//远程缓存策略
        REMOTE_CHOICE(HonorEvents.TASK_CHOICE),//远程权重策略
        REMOTE_FORK(HonorEvents.TASK_FORK),//远程遍历策略

        LOCAL(HonorEvents.TASK_MONITOR),
        LOCAL_MONITOR(HonorEvents.TASK_EXECUTE),//本地监听器
        LOCAL_EXECUTER(HonorEvents.TO_CLOSED),//本地执行器

        //闲置状态
//        IDLE,
        CLOSED(HonorEvents.TO_READY),

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
