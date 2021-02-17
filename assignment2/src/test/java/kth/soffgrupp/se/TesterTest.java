package kth.soffgrupp.se;

import static org.junit.Assert.*;

import kth.soffgrupp.se.exceptions.TestingException;
import org.junit.Test;

import kth.soffgrupp.se.exceptions.CompilationException;

public class TesterTest {

    /**
     * Test that tester can test a "hello world" project
     */
    @Test
    public void testTesterSuccess() {
        String path = "test2";
        GitHandler git = new GitHandler(path);
        Tester tester = new Tester();
        BuildLogger log = new BuildLogger();

        // Clone a mock repo containing a simple project that should compile
        try {
            git.checkout("https://github.com/soffgrupp6/assignment2_test.git", "main");
            tester.test(log, path + "/test");
        } catch(Exception e){
            git.clean();
            fail("There was an exception: " + e);
        }

        // Test that the testing was run successfully
        assertTrue("Compilation was not successful", log.isTest_success());

        // Test that the logging is successful
        assertEquals(1, log.getTests_run());
        assertEquals(0, log.getTests_errors());
        assertEquals(0, log.getTests_failed());
        git.clean();
    }
}

