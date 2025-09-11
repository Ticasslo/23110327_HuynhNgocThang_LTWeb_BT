package ngocthang.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.Category;
import ngocthang.services.ICategoryService;
import ngocthang.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/admin/categories"})
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String url = request.getRequestURI();
        
        if (url.contains("categories")) {
            List<Category> list = categoryService.getAll();
            request.setAttribute("listcate", list);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-list.jsp");
            rd.forward(request, response);
        } else if (url.contains("add")) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-add.jsp");
            rd.forward(request, response);
        } else if (url.contains("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Category category = categoryService.get(id);
            request.setAttribute("cate", category);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-edit.jsp");
            rd.forward(request, response);
        } else if (url.contains("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                categoryService.delete(id);
            } catch (Exception e) {
                // Log error but don't print stack trace in production
                System.err.println("Error deleting category: " + e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String url = request.getRequestURI();
        
        if (url.contains("insert")) {
            String catename = request.getParameter("name");
            String icon = request.getParameter("icon");
            // Lấy userid từ session thay vì từ form
            ngocthang.entity.User currentUser = ngocthang.utils.SessionUtils.getUser(request);
            int userid = currentUser != null ? currentUser.getId() : 1; // Default admin ID
            
            Category category = new Category();
            category.setName(catename);
            category.setIcon(icon);
            category.setUserid(userid);
            
            categoryService.insert(category);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        } else if (url.contains("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String catename = request.getParameter("name");
            String icon = request.getParameter("icon");
            // Lấy userid từ session thay vì từ form
            ngocthang.entity.User currentUser = ngocthang.utils.SessionUtils.getUser(request);
            int userid = currentUser != null ? currentUser.getId() : 1; // Default admin ID
            
            Category category = new Category();
            category.setId(id);
            category.setName(catename);
            category.setIcon(icon);
            category.setUserid(userid);
            
            categoryService.edit(category);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        }
    }
}