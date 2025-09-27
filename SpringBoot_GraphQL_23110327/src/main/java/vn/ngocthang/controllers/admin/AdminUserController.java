package vn.ngocthang.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.ngocthang.entity.User;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.RandomUtils;
import vn.ngocthang.utils.UploadHelper;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Tự động thêm user vào tất cả các view
    @ModelAttribute("user")
    public Object addUserToModel(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
    }

    @GetMapping("")
    public String list(ModelMap model) {
        // Gọi hàm findAll() trong service
        List<User> list = userService.findAll();
        // Chuyển dữ liệu từ list lên biến users
        model.addAttribute("users", list);
        return "admin/users/list";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        User user = new User();
        // Chuyển dữ liệu từ model vào biến user để đưa lên view
        model.addAttribute("user", user);
        return "admin/users/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @ModelAttribute("user") User user, 
                                   @RequestParam(name = "password", required = false) String password,
                                   @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        
        // Tạo ID mới nếu là user mới
        if (user.getId() == 0) {
            int newId = RandomUtils.getRandomId();
            while (userService.existsById(newId)) {
                newId = RandomUtils.getRandomId();
            }
            user.setId(newId);
        }
        
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
            return new ModelAndView("admin/users/addOrEdit", model);
        }

        // Mặc định vai trò nếu không chọn (tránh vi phạm CHECK roleid IN (1,2))
        if (user.getRoleid() != 1 && user.getRoleid() != 2) {
            user.setRoleid(2);
        }

        // Nhận mật khẩu từ form hoặc tạo mật khẩu mặc định
        if (password != null && !password.trim().isEmpty()) {
            user.setPassWord(password.trim());
        } else if (user.getId() == 0) {
            user.setPassWord("123456"); // Mật khẩu mặc định cho user mới
        }
        
        // Set createdDate cho user mới
        if (user.getId() == 0) {
            user.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
        }
        
        // Xử lý upload avatar
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UploadHelper.save(imageFile);
            user.setAvatar(fileName);
        }

        // Kiểm tra trùng lặp (chỉ cho user mới)
        if (user.getId() == 0) {
            if (userService.existsByUserName(user.getUserName())) {
                model.addAttribute("alert", "Tên đăng nhập đã tồn tại!");
                return new ModelAndView("admin/users/addOrEdit", model);
            }

            if (userService.existsByEmail(user.getEmail())) {
                model.addAttribute("alert", "Email đã tồn tại!");
                return new ModelAndView("admin/users/addOrEdit", model);
            }

            if (user.getPhone() != null && !user.getPhone().isEmpty() && userService.existsByPhone(user.getPhone())) {
                model.addAttribute("alert", "Số điện thoại đã tồn tại!");
                return new ModelAndView("admin/users/addOrEdit", model);
            }
        }

        try {
            // Gọi hàm save trong service
            userService.save(user);
            
            // Đưa thông báo về cho biến message
            String message = "User is saved!!!!!!!!";
            model.addAttribute("message", message);
            
            // Redirect về URL controller
            return new ModelAndView("redirect:/admin/users/searchpaginated");
        } catch (Exception e) {
            model.addAttribute("alert", "Lỗi khi lưu người dùng: " + e.getMessage());
            return new ModelAndView("admin/users/addOrEdit", model);
        }
    }

    @GetMapping("/edit/{userId}")
    public ModelAndView edit(ModelMap model, @PathVariable("userId") Integer userId) {
        // Kiểm tra sự tồn tại của user
        Optional<User> optUser = userService.findById(userId);
        User user = new User();
        
        if (optUser.isPresent()) {
            // Copy từ entity sang user
            user = optUser.get();
            // Đẩy dữ liệu ra view
            model.addAttribute("user", user);
            return new ModelAndView("admin/users/addOrEdit", model);
        } else {
            model.addAttribute("message", "User is not existed!!!!");
            return new ModelAndView("redirect:/admin/users");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, ModelMap model) {
        userService.deleteById(id);
        return "redirect:/admin/users?success=1";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, ModelMap model) {
        List<User> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = userService.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(keyword.trim(), keyword.trim());
        } else {
            items = userService.findAll();
        }

        model.addAttribute("users", items);
        model.addAttribute("keyword", keyword);
        return "admin/users/search";
    }

    @GetMapping("/searchpaginated")
    public String searchPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 ModelMap model) {
        
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("fullName"));
        Page<User> resultPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            resultPage = userService.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(keyword.trim(), keyword.trim(), pageable);
            model.addAttribute("keyword", keyword);
        } else {
            resultPage = userService.findAll(pageable);
        }
        
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, page - 2);
            int end = Math.min(page + 2, totalPages);
            
            if (end == totalPages) start = Math.max(1, end - 4);
            else if (start == 1) end = Math.min(start + 4, totalPages);
            
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        model.addAttribute("userPage", resultPage);
        return "admin/users/searchpaginated";
    }
}