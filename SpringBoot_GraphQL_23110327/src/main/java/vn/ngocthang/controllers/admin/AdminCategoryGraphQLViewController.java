package vn.ngocthang.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller xử lý các view cho Category management sử dụng GraphQL
 */
@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryGraphQLViewController {

    /**
     * Hiển thị danh sách categories
     */
    @GetMapping("")
    public String list() {
        return "admin/categories/list";
    }

    /**
     * Hiển thị form thêm category mới
     */
    @GetMapping("/add")
    public String add() {
        return "admin/categories/addOrEdit";
    }

    /**
     * Hiển thị form chỉnh sửa category
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id) {
        return "admin/categories/addOrEdit";
    }

    /**
     * Hiển thị trang tìm kiếm categories
     */
    @GetMapping("/search")
    public String search() {
        return "admin/categories/search";
    }

    /**
     * Hiển thị trang tìm kiếm categories có phân trang
     */
    @GetMapping("/searchpaginated")
    public String searchPaginated() {
        return "admin/categories/searchpaginated";
    }
}
