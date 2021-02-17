package kth.soffgrupp.se;

import java.io.*;
import java.util.ArrayList;
import kth.soffgrupp.se.exceptions.TestingException;

/**
 * This class runs the tests on the Maven project in the directory ./test/assignment2/
 */

class Tester {
    /**
     * The test method opens a new process to run the project tests.
     * The stdout and stderror of the compilation process is printed to the servers stdout.
     * In the case of an IOException, the exceptions stacktrace is printed to the servers stdout.
     * The method writes the test results to the log object.
     *
     * @param log                 Object for logging the build
     * @throws TestingException
     */

    public void test(BuildLogger log) throws TestingException {
        try {
            // Run tests
            Process p = Runtime.getRuntime().exec("mvn -f test/assignment2 test");
            //Process p = Runtime.getRuntime().exec("mvn test");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderror = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String s;
            ArrayList<String> errors = new ArrayList<>();

            while((s = stdin.readLine()) != null) {

                //Check test output for results
                System.out.println(s);
                if (s.contains("[ERROR] Failures:")){
                    while(!(s = stdin.readLine()).contains("[INFO]")){
                        s = s.replace("[ERROR]   ", "");
                        errors.add(s);
                    }
                    log.setErrors_list(errors);
                }
                if (s.contains("Tests run") && s.contains("Failures")){
                    int[] vars = parseStat(s, log);
                    //Log test results
                    log.setTests_run(vars[0]);
                    log.setTests_failed(vars[1]);
                    log.setTests_errors(vars[2]);
                }
                
                if (s.contains("BUILD SUCCESS")){
                    log.setTest_success(true);
                }
                
            }
            
            while((s = stderror.readLine()) != null) {
                System.out.println(s);
            }
            
            //Throw exceptions at fail
            if(log.getTests_errors() > 0 || !log.isTest_success()) {
                throw new RuntimeException("Error in tests");
            }
            
            if(log.getTests_failed() > 0){
                throw new TestingException("Some tests failed");
            }
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * parseStat is a helper method used for parsing the test statistics for easier logging.
     * @param s     String with test statistics
     * @param log   Logging object
     */

    private int[] parseStat(String s, BuildLogger log){
        String[] s_arr = s.split(",");
        int[] vars = new int[3];
        
        for (int i=0; i<3; i++){
            String[] s_instance = s_arr[i].split(":");
            vars[i] = Integer.parseInt(s_instance[1].strip());
        }
        
        return vars;
    }
}
