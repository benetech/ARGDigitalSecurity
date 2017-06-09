/**
 * ConditionExecutor
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.conditions;

import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Step;
import com.argSecurity.model.UserStep;
import com.argSecurity.module.actions.StepException;

public class ConditionExecutor {
	
	/**
	 * 
	 * @param condition
	 * @param step
	 * @param userStep
	 * @return
	 */
	public Message executeCondition(Condition condition, Step step, UserStep userStep) {
		Message msg = null;
		String conditionName = condition.getName();
		String packageName = this.getClass().getPackage().getName();
		String className = packageName + "." + conditionName + "Condition";
		
		try {
			Class<?> conditionClass = Class.forName(className);
			ICondition concreteCond = (ICondition) conditionClass.newInstance();
			msg = concreteCond.execute(condition, step, userStep);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (StepException e) {
			e.printStackTrace();
		}

		return (msg != null) ? msg :
			new Message(
					com.argSecurity.utils.Constants.MessagesCodes.INVALID_DATA,
					com.argSecurity.utils.Constants.AppMessages.INVALID_ACTION);
	}
	
}
