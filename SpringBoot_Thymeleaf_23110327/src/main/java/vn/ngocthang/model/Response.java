package vn.ngocthang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Boolean status;
    private String message;
    private Object body;
    
    public static Response success(String message, Object body) {
        return new Response(true, message, body);
    }
    
    public static Response success(String message) {
        return new Response(true, message, null);
    }
    
    public static Response error(String message) {
        return new Response(false, message, null);
    }
    
    public static Response error(String message, Object body) {
        return new Response(false, message, body);
    }
}
