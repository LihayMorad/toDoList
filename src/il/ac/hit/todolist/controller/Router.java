package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import il.ac.hit.todolist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Router
 */
@WebServlet("/router/*")
public class Router extends HttpServlet {

    private static String packageName = "il.ac.hit.todolist.controller";

    // Constructor
    public Router() {
        super();
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("~~~[doOptions]~~~");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Origin", "*");

//        doGet(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("~~~[doDelete]~~~");

        doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("~~~[doPost]~~~");

//        System.out.println(response.getContentType());
//        response.sendRedirect(request.getContextPath() + "/index.jsp");

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("~~~[doGet]~~~");

        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length < 3) {
            response.setStatus(404);
        } else {

            String controller = splittedURL[1];
            String action = splittedURL[2];
            System.out.println("Controller: " + controller);
            System.out.println("Action: " + action);

            String controllerClassName = controller.substring(0, 1).toUpperCase() + controller.substring(1) + "Controller";
            //composing the controller class name
            try {
                Class type = Class.forName(packageName + "." + controllerClassName);
                Constructor constructor = type.getConstructor(HttpServletRequest.class, HttpServletResponse.class);
                Object controllerInstance = constructor.newInstance(request, response);
                Method requestedAction = type.getMethod(action);
                requestedAction.invoke(controllerInstance);
//            getServletContext().getRequestDispatcher("/" + action + ".jsp").forward(request, response);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                    SecurityException | IllegalArgumentException | InvocationTargetException e) {
                //throw new toDoListException(error.getMessage(),error);
                e.printStackTrace();

                //sending the user to error message screen
                response.sendError(404, "Something went wrong");
            }
        }
    }

}
