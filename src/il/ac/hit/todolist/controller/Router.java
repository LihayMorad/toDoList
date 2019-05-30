package il.ac.hit.todolist.controller;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.HashMap;
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
	private static final long serialVersionUID = 1L;

	private static String packageName = "il.ac.hit.todolist.controller";

	// Constructor
	public Router() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, Boolean> redirect = new HashMap<>();
		redirect.put("redirect", true);
		String json = new Gson().toJson(redirect);
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

//        doGet(request, response); // TEMPORARY
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String splittedURL[] = request.getPathInfo().split("/");
		String controller = splittedURL[1];
		String action = splittedURL[2];
		System.out.println("Controller: " + controller);
		System.out.println("Action: " + action);

		String controllerClassName = controller.substring(0, 1).toUpperCase() + controller.substring(1) + "Controller";
		//composing the controller class name
		try {
			Class type = Class.forName(packageName + "." + controllerClassName);
			Object controllerInstance = type.newInstance();
			//Object controllerInstance = type.newInstance(request,response);
			Method requestedAction = type.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			requestedAction.invoke(controllerInstance, request, response);
//            getServletContext().getRequestDispatcher("/" + action + ".jsp").forward(request, response);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
				SecurityException | IllegalArgumentException | InvocationTargetException e) {
			//throw new toDoListException(error.getMessage(),error);
			e.printStackTrace();
			//sending the user to error message screen
		}

		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//out.println("<br/>getRequestURI():"+request.getRequestURI());
		//out.println("<br/>getRequestURL():"+request.getRequestURL().toString());
		//out.println("<br/>getServletPath():"+request.getServletPath());
		//out.println("<br/>getQueryString():"+request.getQueryString());
		//out.println("<br/>getPathInfo():"+request.getPathInfo());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
}
