package kth.soffgrupp.se;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.kohsuke.github.GHCommitState;
import org.kohsuke.github.GHCommitStatus;
import org.kohsuke.github.GHRepository;


public class NotifierTest {
	
    /**
     * Test that update commit status runs
     */
    @Test
    public void testUpdateCommitStatus()
    {
        class RepoTest extends GHRepository {
            public boolean hasRun = false;

            public RepoTest() {
            }

            @Override
            public GHCommitStatus createCommitStatus(String o0, GHCommitState o1, String o2, String o3, String o4) {
                hasRun = true;
                return null;
            }
        }
        try {
            RepoTest repo = new RepoTest();
            Notifier notifier = new Notifier(repo);
            notifier.setCommitStatus("sha", "description", GHCommitState.SUCCESS);
            assertTrue(repo.hasRun);
        } catch(Exception ex) {
            fail("There was an exception: " + ex);
        }
    }

    /**
     * Test that update commit status throws NullPointerException for no repo data
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateCommitStatusThrowsForEmptyData()
    {
        GHRepository repo = new GHRepository();
        Notifier notifier = new Notifier(repo);
        try {
            notifier.setCommitStatus("sha", "description", GHCommitState.SUCCESS);
        } catch (IOException e) {

        }
    }
    
}
