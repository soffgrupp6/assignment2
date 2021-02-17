package kth.soffgrupp.se;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * The purpose of this class is to function as a Git interface to be able to clone and checkout a repo
 */

class GitHandler {
    
    private File directory;
    
    public GitHandler(String dir) {
        directory = new File(dir);
    }

    /**
     * Checkout a certain branch on a repo
     * 
     * @param repo the repository URL
     * @param branch the branch name
     */
    public void checkout(String repo, String branch) throws GitAPIException {
    	List<String> branches = new ArrayList<String>();
    	branches.add(branch);

		Git git = Git.cloneRepository()
		    .setURI(repo)
		    .setDirectory(directory)
		    .setBranchesToClone(branches)
		    .setBranch(branch)
		    .call();

    }
    
    /**
     * Do some clean up of the directory
     */
    public void clean() {
    	try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
}