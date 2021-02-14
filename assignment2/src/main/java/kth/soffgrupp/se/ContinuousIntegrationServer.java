package kth.soffgrupp.se;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.*;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    GHRepository repo;

    public ContinuousIntegrationServer() {
        try {
            GitHub git_api = GitHubBuilder.fromPropertyFile().build();
            //assert git_api.isCredentialValid() == true;
            repo = git_api.getRepository​("soffgrupp6/assignment2");

        } catch(java.io.IOException e) {
            System.err.println(e);
        }
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
    private void setCommitStatus(String sha, String description, GHCommitState status) {
        try {
            repo.createCommitStatus(sha, status, "", description, "CI Server");
        } catch (java.io.IOException e) {
            System.err.println(e);
        }
    }

    /**
     * This function handles a POST request. This is run
     * when there is an incoming webhook from GitHub.
     */
    private void handlePost(String target,
                            Request baseRequest,
                            HttpServletRequest request,
                            HttpServletResponse response)
        throws IOException, ServletException
    {
        GitHandler git;
        Compiler compiler;
        Tester tester;
        
        // Read the request
        JSONObject data = new JSONObject(request.getReader().readLine());
        JSONObject repository = data.getJSONObject("repository");
        
        String repo = repository.getString("clone_url");
        String branch = repository.getString("default_branch");
        String dest_path = "test";
        
        git = new GitHandler(dest_path);
        
        try {
            // Checkout the Git branch
            git.checkout(repo, branch);

            // Compile the code
            compiler = new Compiler();
            compiler.compile();

            // Test the code
            tester = new Tester();
            tester.test();
            
        } catch(Exception ex) {
            System.out.println("There was a failure in some step. The steps above should throw the appropriate error on failure.");
        }
        
        git.clean();

        // Send response
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        System.out.print("POST: ");
        System.out.println(target);
    }

    /**
     * This function handles a GET request. If the site is requested
     * in a browser it is served using this function.
     */
    private void handleGet(String target,
                            Request baseRequest,
                            HttpServletRequest request,
                            HttpServletResponse response)
        throws IOException, ServletException
    {
        if (target.equals("/favicon.ico")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.print("GET:  ");
        System.out.println(target);

        if (target.equals("/")) {
            // Here we serve the list of builds
            String buildString = "<p>This is a build</p>";
            buildString += "<p>This is another build</p>";
            response.getWriter().println("<!DOCTYPE html><html><body><h1><a href=\"/\">CI Server</a></h1><p>This is the list of builds.</p>" + buildString + "</body></html>");
        } else {
            // Here we serve the information from a specific build
            response.getWriter().println("<!DOCTYPE html><html><body><h1><a href=\"/\">CI Server</a></h1><p>This is build " + target + ".</p></body></html>");
        }
    }

    /**
     * This function is called for all incoming requests to the server.
     * It handles incoming webhooks and runs the CI server, reporting back to
     * GitHub. It also serves a web interface with information on the build status
     * and test status of different tests.
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {
        if (baseRequest.getMethod().equals("GET"))
            handleGet(target, baseRequest, request, response);
        else
            handlePost(target, baseRequest, request, response);
    }

    /**
     * This function starts the CI server
     */
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
