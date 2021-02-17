package kth.soffgrupp.se;

import java.io.*;

/**
 * This class compiles the source files in the directory ./test/assignment2/
 */
import kth.soffgrupp.se.exceptions.CompilationException;

class Compiler {
	
    /**
     * The compile function opens a new process to compile the project into a new jar file.
     * The stdout and stderror of the compilation process is printed to the servers stdout.
     * In the case of an IOException, the exceptions stacktrace is printed to the servers stdout.
     */
    public void compile(BuildLogger log, String path) throws CompilationException {
        try {
            Process p = Runtime.getRuntime().exec("mvn -f " + path + " clean compile assembly:single");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderror = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String s = null;

            while((s = stdin.readLine()) != null) {
                if(s.contains("BUILD SUCCESS")){
                    log.setCompile_success(true);
                }
            }
            
            while((s = stderror.readLine()) != null) {
                System.out.println(s);
            }
            
            if(!log.isCompile_success())
                throw new CompilationException("Failed to compile");
            
            return;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
