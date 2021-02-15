package kth.soffgrupp.se;

import java.io.*;

class Compiler {
    /**
     * The compile function opens a new process to compile the project into a new jar file.
     * The stdout and stderror of the compilation process is printed to the servers stdout.
     * In the case of an IOException, the exceptions stacktrace is printed to the servers stdout. 
     */
    public void compile() throws RuntimeException {
        try {
            Process p = Runtime.getRuntime().exec("cd test/assignment2 && mvn clean compile assembly:single");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderror = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String s = null;
            boolean success = false;

            while((s = stdin.readLine()) != null) {
                if(s.contains("BUILD SUCCESS"))
                    success = true;
                System.out.println(s);
            }
            while((s = stderror.readLine()) != null) {
                System.out.println(s);
            }
            if(success == false)
                throw new RuntimeException("Failed to compile");
            return;
        }
        catch(IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
