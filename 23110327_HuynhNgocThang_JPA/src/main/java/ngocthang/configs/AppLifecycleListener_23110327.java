package ngocthang.configs;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener_23110327 implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Đóng EntityManagerFactory để dừng các thread nền của Hibernate
        JPAConfig_23110327.shutdown();

        // Hủy đăng ký JDBC drivers để tránh memory leak khi reload context
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ignored) {
            }
        }
    }
}


