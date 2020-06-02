package se.bjurr.gitchangelog.internal.integrations.redmine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.taskadapter.redmineapi.Params;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Identifiable;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;

public class AbstractRedmineApi {
	protected 	RedmineManager redMgr;
	protected DateFormat redmineDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public RedmineManager connect(final String uri, final String apiAccessKey){		
		redMgr =  RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
		redMgr.setObjectsPerPage(100);	
		return redMgr;
	}

	public void keepIssueWithMilestone(final String milestone, final List<Issue> issueList) {
		if(milestone != null) {
			final List<Issue> issueToRemove = new ArrayList<Issue>();
			for(final Iterator<Issue> it = issueList.iterator(); it.hasNext();) {
				final Issue issue = it.next();
				if(issue.getTargetVersion() == null || !issue.getTargetVersion().getName().equals(milestone)) {
					issueToRemove.add(issue);	
				}				
			}
			issueList.removeAll(issueToRemove);
		}
	}

	
	public List<Issue> listSubIssues(final Integer parentIssueId) throws RedmineException {
		final Params params = new Params()
				.add("parent_id", parentIssueId.toString())
				.add("status_id", "*"); 								
		return redMgr.getIssueManager().getIssues(params).getResults();
	}
	
	
	
	
}

