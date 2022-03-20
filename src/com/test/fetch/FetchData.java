package com.test.fetch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/search")
public class FetchData extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String rollno = req.getParameter("roll");
		int roll = Integer.valueOf(rollno);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/madhanpractice","root","tiger");

			PreparedStatement ps = con.prepareStatement("select*from student where rollno=?");
			ps.setInt(1, roll);

			out.print("<table width=50% border=1>");
			out.print("<caption>Result::</caption>");

			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int total = rsmd.getColumnCount();

			out.print("<tr>");

			for (int i=1; i<=total; i++) {
				out.print("<th>" + rsmd.getColumnName(i) + "</th>");
			}

			out.print("</tr>");

			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3)+ "</td><td>" + rs.getString(4) + "</td></tr>");;
			}
			out.print("</table>");

		} catch (Exception e) {
			e.printStackTrace();

		}
		finally {
			out.close();
		}
	}

}
