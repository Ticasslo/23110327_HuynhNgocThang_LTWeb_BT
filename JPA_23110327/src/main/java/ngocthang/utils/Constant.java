package ngocthang.utils;

public class Constant {
    public static final String SESSION_ACCOUNT = "account";
    public static final String COOKIE_REMEMBER = "username";
    
    // Role constants
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_MANAGER = 2;
    public static final int ROLE_USER = 5;
    
    public static class Path {
        public static final String REGISTER = "/views/register.jsp";
        public static final String LOGIN    = "/views/login.jsp";
        public static final String ADMIN_HOME = "/views/admin/home.jsp";
        public static final String MANAGER_HOME = "/views/manager/home.jsp";
        public static final String USER_HOME = "/views/user/home.jsp";
    }
    //Upload directory - relative to project root
    public static final String DIR = "uploads";
}
