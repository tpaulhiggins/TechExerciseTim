
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteFlavors")
public class DeleteFlavors extends HttpServlet {
   private static final long serialVersionUID = 1L;
   int delete = 0;

   public DeleteFlavors() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String item_name = request.getParameter("deleteName");
      if (item_name.length() == 0) {
    	  item_name = null;
      }
      
      Connection connection = null;
      //Check to see if it is in database
      String insertSqlCheck = "SELECT * FROM inventory where Item_name LIKE ?";
      try {
          DBConnection.getDBConnection();
          connection = DBConnection.connection;
          PreparedStatement preparedStmt = connection.prepareStatement(insertSqlCheck);
          String name = item_name + "%";
          preparedStmt.setString(1, name);
          ResultSet rs = preparedStmt.executeQuery();
          if (rs.next()) {
        	  delete = 0;
          }
          else {
        	  delete = 1;
          }
          connection.close();
       } catch (Exception e) {
          e.printStackTrace();
       }

      String insertSql = "DELETE FROM inventory WHERE Item_Name LIKE ?";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         String name = item_name + "%";
         preparedStmt.setString(1, name);
         int delete = preparedStmt.executeUpdate();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Delete Syrups from DB";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      if(delete == 0) {
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + title + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h2 align=\"center\">" + title + "</h2>\n" + //
                "<ul>\n" + //

                "  <li><b>Item Name: </b>: " + item_name + " has been successfully removed. "+"\n" + //


                "</ul>\n");

      }
      else {
    	  out.println(docType + //
                  "<html>\n" + //
                  "<head><title>" + title + "</title></head>\n" + //
                  "<body bgcolor=\"#f0f0f0\">\n" + //
                  "<h2 align=\"center\">" + title + "</h2>\n" + //
                  "<ul>\n" + //

                  "Item could not be found. Please try again."+"\n" + //


                  "</ul>\n");
      }
      
      out.println("<a href=/webproject/syrupSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}