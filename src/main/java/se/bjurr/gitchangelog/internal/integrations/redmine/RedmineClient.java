package se.bjurr.gitchangelog.internal.integrations.redmine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;

import se.bjurr.gitchangelog.api.exceptions.GitChangelogIntegrationException;

public class RedmineClient extends AbstractRedmineApi {

	private final String hostUrl;

	public RedmineClient(String hostUrl, String apiToken) {
		this.hostUrl = hostUrl;
		connect(hostUrl, apiToken);
	}

	public Optional<RedmineIssue> getIssue(String projectName, Integer matchedIssue)
			throws GitChangelogIntegrationException {
		Project project = null;
		try {
			if(projectName != null)
			project = redMgr.getProjectManager().getProjectByKey(projectName);
		} catch (Exception e) {
			throw new GitChangelogIntegrationException("Unable to find project \"" + projectName
					+ "\". It should be \"tomas.bjerre85/violations-test\" for a repo like: https://gitlab.com/tomas.bjerre85/violations-test",
					e);
		}
		try {
			Issue issueById = redMgr.getIssueManager().getIssueById(matchedIssue);
			Issue issue = issueById;

			if(project != null && issue.getProjectId() != project.getId()) {
				return Optional.absent();
			}
			return Optional.of(createRedmineIssue(issue));

		} catch (Exception e) {
			throw new GitChangelogIntegrationException(e.getMessage(), e);
		}
	}
	
	public String getHostUrl() {
		return hostUrl;
	}

	private RedmineIssue createRedmineIssue(Issue candidate) {
		String title = candidate.getSubject();
		String link = hostUrl + "/issues/" + candidate.getId();
		String desc = candidate.getDescription();
		Collection<Issue> linked = candidate.getChildren();
		List<String> linkedIds = new ArrayList<>();
		for (Issue issue : linked) {
			linkedIds.add(String.valueOf(issue.getId()));
		}
		return new RedmineIssue(title, link,desc,linkedIds);
	}

//	private List<RedmineIssue> getAllIssues(String projectName) throws IOException, GitChangelogIntegrationException {
//
//		List<Issue> issues;
//		try {
//			issues = redMgr.getIssueManager().getIssues(projectName, null, null);
//		} catch (RedmineException e) {
//			throw new GitChangelogIntegrationException(e.getMessage(), e);
//		}
//		List<RedmineIssue> result = new ArrayList<>();
//
//		for (Issue issue : issues) {
//			result.add(createRedmineIssue(issue));
//
//		}
//		return result;
//	}
}
