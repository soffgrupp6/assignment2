package kth.soffgrupp.se;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class holds all build information
 */

@Document(collection = "builds", schemaVersion= "1.0")
public class BuildLogger {
    @Id
    private String sha;
    private String start_time;
    private boolean compile_success = false;
    private boolean test_success = false;
    private int tests_run = 0;
    private int tests_failed = 0;
    private int tests_errors = 0;

    public String getSha() {
        return sha;
    }
    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setTime() {
        start_time = LocalDateTime.now().toString();
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public boolean isCompile_success() {
        return compile_success;
    }

    public void setCompile_success(boolean compile_success) {
        this.compile_success = compile_success;
    }

    public boolean isTest_success() {
        return test_success;
    }

    public void setTest_success(boolean test_success) {
        this.test_success = test_success;
    }

    public int getTests_run() {
        return tests_run;
    }

    public void setTests_run(int tests_run) {
        this.tests_run = tests_run;
    }

    public int getTests_failed() {
        return tests_failed;
    }

    public void setTests_failed(int tests_failed) {
        this.tests_failed = tests_failed;
    }

    public int getTests_errors() {
        return tests_errors;
    }

    public void setTests_errors(int tests_errors) {
        this.tests_errors = tests_errors;
    }
}
