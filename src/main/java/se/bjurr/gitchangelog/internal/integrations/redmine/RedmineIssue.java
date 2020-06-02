package se.bjurr.gitchangelog.internal.integrations.redmine;

import java.util.List;

public class RedmineIssue {

	private final String title;
	private final String link;
	private final String description;
	private final List<String> linkedIssues;

	public RedmineIssue(String title, String link, String desc, List<String> linkedIssues) {
		this.title = title;
		this.link = link;
		description = desc;
		this.linkedIssues = linkedIssues;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getLinkedIssues() {
		return linkedIssues;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "RedmineIssue [title=" + title + ", link=" + link + ", description=" + description + ", linkedIssues="
				+ linkedIssues + "]";
	}
}
