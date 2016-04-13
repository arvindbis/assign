package test.handler;

import org.apache.log4j.Logger;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

import com.icat.jbpm.helper.UserDelegation;
import com.icat.jbpm.workflow.caseregistration.dev.AssignCSCEngineerHandler;

public class PrincipalAssignment implements AssignmentHandler {

private Logger log = Logger.getLogger(AssignCSCEngineerHandler.class.getName());
	
	public void assign(Assignable assignee, ExecutionContext executionContext) throws Exception {
		ContextInstance contextInstance = null;
		String actorId = null;
		try {
			//contextInstance = executionContext.getProcessInstance().getContextInstance();
			 contextInstance = executionContext.getProcessInstance().getSuperProcessToken()
			                  .getProcessInstance().getContextInstance();
			actorId = UserDelegation.getInstance().getDelegateeUserId((String) (contextInstance
					.getVariable("100")));
			executionContext.getTaskInstance().setDescription("Verification of pupil and Clerk by principal");
		/*	executionContext.getTaskInstance().setVariable(ICatUserIdentifier.DOCKETNO, 
					contextInstance.getVariable(ICatUserIdentifier.DOCKETNO));*/
			executionContext.getTaskInstance().start();
		/*	int deptId = JBPMHandler.getInstance().getDeptIdFromJBPMContext(contextInstance.getProcessInstance().getId());
			int docketId = Integer.parseInt(contextInstance.getVariable(ICatUserIdentifier.DOCKETID).toString());*/
			assignee.setActorId(actorId);
		} catch (Exception e) {
			log.warn("Exception "+e.getMessage());
			executionContext.getTaskInstance().setActorId(String.valueOf(actorId));
		} finally {
			log = null;
		}
	}

}
