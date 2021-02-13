package kth.soffgrupp.se;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

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
    public void checkout(String repo, String branch) {
    	List<String> branches = new ArrayList<String>();
    	branches.add(branch);
    	
        try {
			Git git = Git.cloneRepository()
			    .setURI(repo)
			    .setDirectory(directory)
			    .setBranchesToClone(branches)
			    .setBranch(branch)
			    .call();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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