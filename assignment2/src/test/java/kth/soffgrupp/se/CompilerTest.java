package kth.soffgrupp.se;

import static org.junit.Assert.*;

import org.junit.Test;

import kth.soffgrupp.se.exceptions.CompilationException;

public class CompilerTest {
	
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
     * Test that compiler throws CompilationException if failing to compile
     */
    @Test(expected = CompilationException.class)
    public void testCompileThrowsException() throws CompilationException {
        String path = "this_does_not_exist";
        Compiler compiler = new Compiler();
        BuildLogger log = new BuildLogger();
        
        compiler.compile(log, path);
    }

}
