package com.hit.todo.controller;

import java.io.IOException;
import java.lang.reflect.*;

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	  private String controller;
	  private String action;
	
	
	public Router() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private String formatControlerName() {
		StringBuilder controllerClassName = new StringBuilder(50);
		controllerClassName.append(controller.substring(0,1).toUpperCase()).
                            append(controller.substring(1)).append("Controller");
	    
		return controllerClassName.toString();
	}
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadControllerAndAction(request);
		String controllerClassName=formatControlerName();
		try {
			Class type=Class.forName(controllerClassName);
			Object controllerInstance=type.newInstance();
			Method requestedAction=type.getMethod(action,HttpServletRequest.class,HttpServletResponse.class);
			requestedAction.invoke(controllerInstance, request,response);
			getServletContext().getRequestDispatcher("/"+action+"jsp");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException error) {
			//throw new toDoListException(error.getMessage(),error);
		}
	}
	
	
	private void loadControllerAndAction(HttpServletRequest request) {
		String [] pathInfo= request.getPathInfo().split("/");
		controller=pathInfo[1];
		action=pathInfo[2];
   }
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
