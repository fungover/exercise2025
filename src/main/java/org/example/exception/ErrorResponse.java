package org.example.exception;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {
    public static class Violation {
        public String field;
        public String message;
        public Violation(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

    public Instant timestamp = Instant.now();
    public int status;
    public String error;     // "Bad Request", "Not Found", "Internal Server Error"
    public String message;   // sammanfattning eller detaljer
    public String path;      // request path
    public List<Violation> violations; // valideringsfel

    public ErrorResponse status(int s) { this.status = s; return this; }
    public ErrorResponse error(String e) { this.error = e; return this; }
    public ErrorResponse message(String m) { this.message = m; return this; }
    public ErrorResponse path(String p) { this.path = p; return this; }
    public ErrorResponse violations(List<Violation> v) { this.violations = v; return this; }
}
