package com.example.demo.state;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.HashMap;
import java.util.Map;

@WithStateMachine
public class Tasks {

	private final static Log log = LogFactory.getLog(Tasks.class);

//	private StateMachine<TaskStateMachineConfigurer.States, TaskStateMachineConfigurer.Events> stateMachine;

	private final Map<String, Boolean> tasks = new HashMap<String, Boolean>();

	public Tasks() {
		tasks.put("T1", true);
		tasks.put("T2", true);
		tasks.put("T3", true);
	}

	/*public void run() {
		stateMachine.sendEvent(TaskStateMachineConfigurer.Events.RUN);
	}

	public void fix() {
		tasks.put("T1", true);
		tasks.put("T2", true);
		tasks.put("T3", true);
		stateMachine.sendEvent(TaskStateMachineConfigurer.Events.FIX);
	}*/

	public void fail(String task) {
		if (tasks.containsKey(task)) {
			tasks.put(task, false);
		}
	}

	@TaskStateMachineConfigurer.StatesOnTransition(target = TaskStateMachineConfigurer.States.T1)
	public void taskT1(ExtendedState extendedState) {
		runTask("T1", extendedState);
	}

	@TaskStateMachineConfigurer.StatesOnTransition(target = TaskStateMachineConfigurer.States.T2)
	public void taskT2(ExtendedState extendedState) {
		runTask("T2", extendedState);
	}

	@TaskStateMachineConfigurer.StatesOnTransition(target = TaskStateMachineConfigurer.States.T3)
	public void taskT3(ExtendedState extendedState) {
		runTask("T3", extendedState);
	}

	@TaskStateMachineConfigurer.StatesOnTransition(target = TaskStateMachineConfigurer.States.AUTOMATIC)
	public void automaticFix(ExtendedState extendedState) {
		Map<Object, Object> variables = extendedState.getVariables();
		variables.put("T1", true);
		tasks.put("T1", true);
	}
//end::snippetA[]

	private void runTask(String task, ExtendedState extendedState) {
		log.info("run task on " + task);
		sleep(2000);
		extendedState.getVariables().put(task, tasks.get(task));
		log.info("run task on " + task + " done");
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public String toString() {
		return "Tasks " + tasks;
	}

}
