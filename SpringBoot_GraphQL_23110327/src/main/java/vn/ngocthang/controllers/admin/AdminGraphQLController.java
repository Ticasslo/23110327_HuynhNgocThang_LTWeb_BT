package vn.ngocthang.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller cho GraphQL Demo
 * Hiển thị trang demo GraphQL vs REST
 */
@Controller
@RequestMapping("/admin")
public class AdminGraphQLController {

    /**
     * Hiển thị trang GraphQL Demo
     * URL: /admin/graphql-demo
     */
    @GetMapping("/graphql-demo")
    public String graphqlDemo() {
        return "admin/graphql-demo";
    }

    /**
     * Hiển thị trang so sánh REST vs GraphQL
     * URL: /admin/api-comparison
     */
    @GetMapping("/api-comparison")
    public String apiComparison() {
        return "admin/api-comparison";
    }
}
