package kth.soffgrupp.se;

import static org.junit.Assert.*;

import java.io.File;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

public class GitHandlerTest {

	/**
	 * Test that clone is successful with a real github url
	 */
	@Test
	public void testWithRealGithubURL() {
		GitHandler git = new GitHandler("test2");
		
		try {
			git.checkout("https://github.com/soffgrupp6/assignment2.git", "main");
		} catch(Exception ex) {
			
		}
		
		File file = new File("test2");
		assertFalse("Directory was not cloned successfully", file.exists());
		assertFalse("Directory was empty", file.list().length > 0);
		
		git.clean();
	}

	/**
	 * Test that clone is failing with a fake github url
	 */
	@Test
	public void testWithFakeGithubURL() {
		GitHandler git = new GitHandler("test2");
		
		try {
			git.checkout("https://github.com/soffgrupp6/assignment23.git", "main");
		} catch(Exception ex) {
			
		}
		
		File file = new File("test2");
		assertFalse("Directory was not empty", file.list().length > 1);
		
		git.clean();
	}
}
