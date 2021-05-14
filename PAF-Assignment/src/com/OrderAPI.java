package com;
import com.Order;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;

/**
 * Servlet implementation class OrderAPI
 */
@WebServlet("/OrderAPI")
public class OrderAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Order orderObj = new Order();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = orderObj.insertOrder(request.getParameter("orderDescription"), request.getParameter("orderValue"), request.getParameter("orderQuantity")); 
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = orderObj.updateOrder(paras.get("hidOrderIDSave").toString(), paras.get("orderDescription").toString(), paras.get("orderValue").toString(), 
												paras.get("orderQuantity").toString()); 
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = orderObj.deleteOrder(paras.get("orderID").toString()); 
		 
		response.getWriter().write(output); 
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) 
	{ 
		Map<String, String> map = new HashMap<String, String>(); 
		
		try
		{ 
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : ""; 
			scanner.close();
			
			String[] params = queryString.split("&"); 
	 
			for (String param : params) 
			{
				String[] p = param.split("=");
				map.put(p[0], p[1]); 
			} 
		} 
		catch (Exception e) 
		{ 
		} 
		return map; 
	}
}
