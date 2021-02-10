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

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{

    public void handle(String target,
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
        
        // Log results
        // .....

        // Send repsonse. This is probably something Git will use to set branch status e.t.c.
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        response.getWriter().println();
        

    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}