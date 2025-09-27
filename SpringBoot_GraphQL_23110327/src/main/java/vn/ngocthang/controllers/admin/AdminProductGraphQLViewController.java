package vn.ngocthang.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller cho Product Management UI
 * Chỉ serve các trang view, logic CRUD qua GraphQL
 */
@Controller
@RequestMapping("/admin/products")
public class AdminProductGraphQLViewController {

    /**
     * Trang danh sách sản phẩm với GraphQL + AJAX
     * URL: /admin/products
     */
    @GetMapping("")
    public String list() {
        return "admin/products/list";
    }

    /**
     * Trang thêm sản phẩm với GraphQL + AJAX
     * URL: /admin/products/add
     */
    @GetMapping("/add")
    public String add() {
        return "admin/products/addOrEdit";
    }

    /**
     * Trang sửa sản phẩm với GraphQL + AJAX
     * URL: /admin/products/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String edit() {
        return "admin/products/addOrEdit";
    }

    /**
     * Trang tìm kiếm sản phẩm với GraphQL + AJAX
     * URL: /admin/products/search
     */
    @GetMapping("/search")
    public String search() {
        return "admin/products/search";
    }

    /**
     * Trang tìm kiếm phân trang với GraphQL + AJAX
     * URL: /admin/products/searchpaginated
     */
    @GetMapping("/searchpaginated")
    public String searchPaginated() {
        return "admin/products/searchpaginated";
    }
}
