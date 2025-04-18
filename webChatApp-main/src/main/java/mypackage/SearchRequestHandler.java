package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;*/

public class SearchRequestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String output = "";
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		
		try {
			Connection conn = new DatabaseConfig().getConnection(); 
			String searchTerm = MySQLUtils.mysql_real_escape_string(conn,req.getParameter("searchTerm") ) ;		// security needed here (--!!! important)
			
			String sql = "SELECT * FROM users WHERE (fname LIKE '%"+searchTerm+"%' OR lname LIKE '%"+searchTerm+"%') AND NOT unique_id='"+ session.getAttribute("unique_id") +"';";
			
			ResultSet set = conn.prepareStatement(sql).executeQuery();
			int i = 0;
			while(set.next()) {
	
				String reciever_id = set.getString("unique_id");
				String sender_id = req.getSession().getAttribute("unique_id")+"";
				ResultSet latestMsgSet = getMessagesResultSet(reciever_id, sender_id, conn);
				int numRow = numOfRows(reciever_id, sender_id, conn);
				
				String result="No message available.";
				
				if(numRow > 0) {
				
					if(latestMsgSet.next()) {						
						result = latestMsgSet.getString("msg");
						
					}
					
				}else {
					result = "No message available.";
				}
				String msg;
				if(result.length() > 26) {
					msg = result.substring(0, 25) + "...";
				}else {
					msg = result;
				}
				// cgeck user in online or offline
				String offline;
			
				if(set.getString("status").equalsIgnoreCase("Offline now")) {
					offline = "offline";
				}else {
					offline = "";
				}
				
					   output += "<a href=\"chats?user_id="+ set.getString("unique_id") +"\">\n"
						   		+ "					<div class=\"content\">\n"
						   		+ "						<img alt=\"\" src=\"uploads/"+ set.getString("img")+"\">\n"
						   		+ "						<div class=\"details\">\n"
						   		+ "							<span>"+ set.getString("fname") + " " + set.getString("lname") +"</span>\n"
						   		+ "							<p>"+msg+"</p>\n"
						   		+ "						</div>\n"
						   		+ "					</div>\n"
						   		+ "				<div class=\"status-dot "+ offline +" \"><i class=\"fas fa-circle\"></i></div>\n"
						   		+ "				</a>";
			   i += 1;			   
		   }
		
			if(i == 0) {
				output += "No Match found.";
			}
			
			out.write(output);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
private int numOfRows(String incoming_id, String outgoing_id, Connection conn) throws SQLException {
		
		ResultSet set = getMessagesResultSet(incoming_id, outgoing_id, conn);
		int i = 0;
		while(set.next()) {
			i += 1;
		}
		return i;
	}

	private ResultSet getMessagesResultSet(String incoming_id, String outgoing_id, Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM messages "
				+ " WHERE (outgoing_msg_id=? AND incoming_msg_id=?)"
				+ " OR (outgoing_msg_id=? AND incoming_msg_id=?) "
				+ "ORDER BY msg_id DESC LIMIT 1;";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, outgoing_id);
		pstmt.setString(2, incoming_id);
		pstmt.setString(3, incoming_id);
		pstmt.setString(4, outgoing_id);
		
		ResultSet set = pstmt.executeQuery();
		
		return set;
		
	}
	
}
