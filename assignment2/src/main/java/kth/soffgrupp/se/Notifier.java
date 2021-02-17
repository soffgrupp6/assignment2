package kth.soffgrupp.se;

import org.kohsuke.github.*;

/**
 * This class uses GitHub API to set commit statuses on commits
 */
public class Notifier {

    GHRepository repo;

    public Notifier(GHRepository repo) {
        this.repo = repo;
    }

    /**
     * Set commit status for commit sha.
     *
     * Status is GHCommitState.ERROR
     *           GHCommitState.PENDING
     *           GHCommitState.FAILURE
     *           GHCommitState.SUCCESS
     *
     * @param sha           Hash value from hexadecimal string
     * @param description   Description of status, short sentence
     * @param status        GHCommitState enum
     */
    public void setCommitStatus(String sha, String description, GHCommitState status) throws java.io.IOException {
        repo.createCommitStatus(sha, status, "", description, "CI Server");
    }
}
