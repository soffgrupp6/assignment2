package kth.soffgrupp.se;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.kohsuke.github.GHCommitState;
import org.kohsuke.github.GHCommitStatus;
import org.kohsuke.github.GHRepository;


/**
 * Unit test for simple App.
 */
public class AppTest
{
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

    /**
     * Test that compiler can compile a "hello world" project
     */
    @Test
    public void testCompileSuccess() {
        String path = "test2";
        GitHandler git = new GitHandler(path);
        Compiler compiler = new Compiler();
        BuildLogger log = new BuildLogger();

        // Clone a mock repo containing a simple project that should compile
        try {
            git.checkout("https://github.com/soffgrupp6/assignment2_test.git", "main");
            compiler.compile(log, path + "/test");
        } catch(Exception e){
            git.clean();
            fail("There was an exception: " + e);
        }
        
        assertTrue("Compilation was not successful", log.isCompile_success());
        git.clean();
    }

    /**
     * Test that compiler throws RuntimeException if failing to compile
     */
    @Test(expected = RuntimeException.class)
    public void testCompileThrowsException() {
        String path = "this_does_not_exist";
        Compiler compiler = new Compiler();
        BuildLogger log = new BuildLogger();

        compiler.compile(log, path);
    }
}
