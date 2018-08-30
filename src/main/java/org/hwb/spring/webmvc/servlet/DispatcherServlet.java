package org.hwb.spring.webmvc.servlet;

import org.hwb.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet, 作为MVC启动的入口
 *
 * @author hwb
 */
public class DispatcherServlet extends HttpServlet {

    private String configLocation = "application.properties";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) {
        try {
            // 1.加载配置文件
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{configLocation});
            System.out.println(applicationContext.getBean("demoController"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
