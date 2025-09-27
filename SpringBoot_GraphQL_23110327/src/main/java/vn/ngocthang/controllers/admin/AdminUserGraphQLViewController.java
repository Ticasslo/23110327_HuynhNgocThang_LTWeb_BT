package vn.ngocthang.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUserGraphQLViewController {

    @GetMapping("")
    public String list() {
        return "admin/users/list";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/users/addOrEdit";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id) {
        return "admin/users/addOrEdit";
    }

    @GetMapping("/search")
    public String search() {
        return "admin/users/search";
    }

    @GetMapping("/searchpaginated")
    public String searchPaginated() {
        return "admin/users/searchpaginated";
    }
}
