package vn.ngocthang.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.ngocthang.entity.User;
import vn.ngocthang.repository.UserRepository;
import vn.ngocthang.utils.RandomUtils;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = userRepository.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(keyword.trim(), keyword.trim());
        } else {
            items = userRepository.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Quản lý người dùng");
        return "admin/users/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Thêm người dùng");
        return "admin/users/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute User user, @RequestParam(name = "password", required = false) String password, Model model) {
        // Tạo ID mới
        int newId = RandomUtils.getRandomId();
        while (userRepository.existsById(newId)) {
            newId = RandomUtils.getRandomId();
        }
        user.setId(newId);
        
        // Chuẩn hóa dữ liệu đầu vào
        if (user.getEmail() != null) user.setEmail(user.getEmail().trim());
        if (user.getUserName() != null) user.setUserName(user.getUserName().trim());
        if (user.getFullName() != null) user.setFullName(user.getFullName().trim());
        if (user.getPhone() != null) user.setPhone(user.getPhone().trim());

        // Required fields
        if (user.getUserName() == null || user.getUserName().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty() ||
            user.getFullName() == null || user.getFullName().isEmpty()) {
            model.addAttribute("alert", "Vui lòng nhập đầy đủ Tên đăng nhập, Email, Họ tên");
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Thêm người dùng");
            return "admin/users/form";
        }

        // Mặc định vai trò nếu không chọn (tránh vi phạm CHECK roleid IN (1,2))
        if (user.getRoleid() != 1 && user.getRoleid() != 2) {
            user.setRoleid(2);
        }

        // Nhận mật khẩu từ form hoặc đặt mặc định nếu trống
        if (password != null && !password.trim().isEmpty()) {
            user.setPassWord(password.trim());
        } else if (user.getPassWord() == null || user.getPassWord().trim().isEmpty()) {
            user.setPassWord("123456");
        }
        
        // Set createdDate
        user.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
        
        // Validate unique
        if (userRepository.existsByUserName(user.getUserName())) {
            model.addAttribute("alert", "Tên đăng nhập đã tồn tại");
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Thêm người dùng");
            return "admin/users/form";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("alert", "Email đã tồn tại");
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Thêm người dùng");
            return "admin/users/form";
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty() && userRepository.existsByPhone(user.getPhone())) {
            model.addAttribute("alert", "Số điện thoại đã tồn tại");
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Thêm người dùng");
            return "admin/users/form";
        }

        try {
            userRepository.saveAndFlush(user);
            return "redirect:/admin/users?success=1";
        } catch (Exception ex) {
            model.addAttribute("alert", "Không thể tạo người dùng: " + ex.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Thêm người dùng");
            return "admin/users/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", opt.get());
        model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
        return "admin/users/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute User input, Model model) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            User exist = opt.get();
            
            // Chuẩn hóa dữ liệu đầu vào
            if (input.getUserName() != null) input.setUserName(input.getUserName().trim());
            if (input.getEmail() != null) input.setEmail(input.getEmail().trim());
            if (input.getFullName() != null) input.setFullName(input.getFullName().trim());
            if (input.getPhone() != null) input.setPhone(input.getPhone().trim());
            
            // Required fields
            if (input.getUserName() == null || input.getUserName().isEmpty() ||
                input.getEmail() == null || input.getEmail().isEmpty() ||
                input.getFullName() == null || input.getFullName().isEmpty()) {
                input.setId(exist.getId());
                model.addAttribute("alert", "Vui lòng nhập đầy đủ Username, Email và Họ tên");
                model.addAttribute("user", input);
                model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
                return "admin/users/form";
            }
            
            // Kiểm tra trùng username (khác user hiện tại)
            if (!exist.getUserName().equals(input.getUserName()) && userRepository.existsByUserName(input.getUserName())) {
                input.setId(exist.getId());
                model.addAttribute("alert", "Tên đăng nhập đã tồn tại");
                model.addAttribute("user", input);
                model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
                return "admin/users/form";
            }

            // Kiểm tra trùng email (khác user hiện tại)
            if (!exist.getEmail().equals(input.getEmail()) && userRepository.existsByEmail(input.getEmail())) {
                input.setId(exist.getId());
                model.addAttribute("alert", "Email đã tồn tại");
                model.addAttribute("user", input);
                model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
                return "admin/users/form";
            }
            
            // Kiểm tra trùng phone (khác user hiện tại)
            if (input.getPhone() != null && !input.getPhone().isEmpty() && 
                !input.getPhone().equals(exist.getPhone()) && userRepository.existsByPhone(input.getPhone())) {
                input.setId(exist.getId());
                model.addAttribute("alert", "Số điện thoại đã tồn tại");
                model.addAttribute("user", input);
                model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
                return "admin/users/form";
            }
            
            // Cập nhật thông tin
            exist.setUserName(input.getUserName());
            exist.setFullName(input.getFullName());
            exist.setEmail(input.getEmail());
            exist.setPhone(input.getPhone());
            exist.setRoleid(input.getRoleid());
            // Không cập nhật password và avatar ở đây
            
            try {
                userRepository.saveAndFlush(exist);
            } catch (Exception ex) {
                input.setId(exist.getId());
                model.addAttribute("alert", "Không thể cập nhật người dùng: " + ex.getMessage());
                model.addAttribute("user", input);
                model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
                return "admin/users/form";
            }
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
