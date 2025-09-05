package ngocthang.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.services.ICategoryService;
import ngocthang.services.impl.CategoryServiceImpl;
import ngocthang.entity.Category;

@WebServlet(urlPatterns = {"/admin/categories", "/admin/category/add", 
                          "/admin/category/insert", "/admin/category/edit", 
                          "/admin/category/update", "/admin/category/delete"})
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
            List<Category> list = categoryService.findAll();
            request.setAttribute("listcate", list);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-list.jsp");
            rd.forward(request, response);
        } else if (url.contains("add")) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-add.jsp");
            rd.forward(request, response);
        } else if (url.contains("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Category category = categoryService.findById(id);
            request.setAttribute("cate", category);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/category-edit.jsp");
            rd.forward(request, response);
        } else if (url.contains("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                categoryService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
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
            String categoryname = request.getParameter("categoryname");
            String images = request.getParameter("images");
            
            Category category = new Category();
            category.setCategoryname(categoryname);
            category.setImages(images);
            
            categoryService.insert(category);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        } else if (url.contains("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String categoryname = request.getParameter("categoryname");
            String images = request.getParameter("images");
            
            Category category = new Category();
            category.setId(id);
            category.setCategoryname(categoryname);
            category.setImages(images);
            
            categoryService.update(category);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        }
    }
}