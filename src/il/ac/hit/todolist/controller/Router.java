package il.ac.hit.todolist.controller;

import java.io.IOException;
import java.lang.reflect.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class Router
 */
@WebServlet("/router/*")
public class Router extends HttpServlet {

    private static String packageName = "il.ac.hit.todolist.controller";
//    private static final Logger LOGGER = Logger.getLogger(Router.class.getSimpleName());

    // Constructor
    public Router() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("~~~[doPost]~~~");

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("~~~[doGet]~~~");

//        LOGGER.info("@@@@@@@@@@@@@doPost"); // IN PROGRESS

        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length < 3) {
            response.sendError(404, "Not found message");

        } else {
            String controller = splittedURL[1];
            String action = splittedURL[2];
            //System.out.println("Controller: " + controller);
            //System.out.println("Action: " + action);

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
                e.printStackTrace();

                //sending the user to error message screen
                request.setAttribute("error", "Something went wrong");
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

}
