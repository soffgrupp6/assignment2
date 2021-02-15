package kth.soffgrupp.se;

import java.time.LocalDateTime;

public class BuildLogger {
    String sha;
    String start_time;
    boolean compile_success;
    boolean test_success;
    int tests_run = 0;
    int tests_failed = 0;
    int tests_errors = 0;

    BuildLogger(String sha){
        this.sha = sha;
        this.start_time = LocalDateTime.now().toString();
        boolean compile_success = false;
        boolean test_success = false;
    }
}
