package se.bjurr.gitchangelog.internal.settings;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.gitchangelog.internal.settings.SettingsIssueType.GITHUB;
import static se.bjurr.gitchangelog.internal.settings.SettingsIssueType.GITLAB;
import static se.bjurr.gitchangelog.internal.settings.SettingsIssueType.JIRA;
import static se.bjurr.gitchangelog.internal.settings.SettingsIssueType.REDMINE;
import java.util.List;

import se.bjurr.gitchangelog.api.GitChangelogApiConstants;

public class IssuesUtil {
  private final Settings settings;

  public IssuesUtil(Settings settings) {
    this.settings = settings;
  }

  public List<SettingsIssue> getIssues() {
    List<SettingsIssue> issues = newArrayList(settings.getCustomIssues());
    addJira(issues);
    addGitHub(issues);
    addGitLab(issues);
    addRedmineIssue(issues);
    return issues;
  }

  
  private void addRedmineIssue(List<SettingsIssue> issues) {
	    if (!isNullOrEmpty(settings.getIssuePattern(SettingsIssueType.REDMINE).get())) {
	      if (settings.getApi(SettingsIssueType.REDMINE).isPresent()) {
	        issues.add(
	            new SettingsIssue(REDMINE, "Redmine", settings.getIssuePattern(SettingsIssueType.REDMINE).or(GitChangelogApiConstants.DEFAULT_REDMINE_ISSUE_PATTERN), null, null));
	      }
	    }
	  }
  
  
  private void addGitHub(List<SettingsIssue> issues) {
    if (!isNullOrEmpty(settings.getGitHubIssuePattern())) {
      if (settings.getGitHubApi().isPresent()) {
        issues.add(
            new SettingsIssue(GITHUB, "GitHub", settings.getGitHubIssuePattern(), null, null));
      }
    }
  }

  private void addGitLab(List<SettingsIssue> issues) {
    if (!isNullOrEmpty(settings.getGitLabIssuePattern())) {
      if (settings.getGitLabServer().isPresent()) {
        issues.add(
            new SettingsIssue(GITLAB, "GitLab", settings.getGitLabIssuePattern(), null, null));
      }
    }
  }

  private void addJira(List<SettingsIssue> issues) {
    if (!isNullOrEmpty(settings.getJiraIssuePattern())) {
      if (settings.getJiraServer().isPresent()) {
        issues.add(
            new SettingsIssue(
                JIRA,
                "Jira",
                settings.getJiraIssuePattern(),
                settings.getJiraServer().or("") + "/browse/${PATTERN_GROUP}",
                null));
      } else {
        issues.add(
            new SettingsIssue(
                JIRA,
                "Jira",
                settings.getJiraIssuePattern(),
                settings.getJiraServer().orNull(),
                null));
      }
    }
  }
}
