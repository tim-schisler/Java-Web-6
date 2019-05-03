package servlets;

import beans.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CartServlet6 extends HttpServlet {

    //method for connecting to the database used in this project.
    private static Connection hw6connection() {
        try {
            // load the driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // get a connection
            String dbURL = "jdbc:mysql://localhost:3306/hw6";
            String username = "student";
            String password = "sesame";
            return DriverManager.getConnection(
                    dbURL, username, password);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the databse driver: ");
            System.out.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error executing the SQL statement: ");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //method for returning all rows of the book table.
    protected static ArrayList<Book> getBookList() {
        ArrayList<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM book";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while( rs.next() ) {    //loop through each book in the DB
                Book book = new Book();
                book.setCoverUrl(rs.getString("Cover"));
                book.setPrice(rs.getDouble("Price"));
                book.setTitle(rs.getString("Title"));
                bookList.add(book);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        //return the list.
        return bookList;
    }
    
    //method for returning a Book object corresponding to one row of the book table.
    protected static Book selectBook(String title) {
        Book book = new Book();
        String query = "SELECT * FROM book WHERE Title = "
                + "\'"
                + title
                + "\'";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if( rs.next() ) {
                book.setCoverUrl(rs.getString("Cover"));
                book.setPrice(rs.getDouble("Price"));
                book.setTitle(rs.getString("Title"));
            }
            rs.close();
            statement.close();
            conn.close();
            
        } catch(SQLException e) {
            System.out.println(e);
        }
        return book;
    }
    
    //method that tests if a user with a given email address already exists in the db.
    protected static boolean userExists(String email) {
        boolean outcome = false;
        String query = "SELECT * FROM user WHERE Email = "
                + "\'"
                + email
                + "\'";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if( rs.next() ) {   //i.e. the result set is not empty
                outcome = true;
            }
            rs.close();
            statement.close();
            conn.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        return outcome;
    }
    
    //method that checks if the password given matches the password
    //for the user identified by the given email address.
    protected static boolean passwordCorrect(String email, String pass) {
        boolean outcome = false;
        String query = "SELECT * FROM user WHERE Email = "
                + "\'"
                + email
                + "\'";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if( rs.next() ) {
                if(rs.getString("Password").equals(pass)) outcome = true;
            } else throw new SQLException("Could not find user in DB to check password.");
            rs.close();
            statement.close();
            conn.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        return outcome;
    }
    
    //method for creating a User object from a row of the user
    //table by searching for a given email address.
    protected static User selectUser(String email) {
        User u = new User();
        String query = "SELECT * FROM user WHERE Email = "
                + "\'"
                + email
                + "\'";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if( rs.next() ) {
                u.setEmail(email);
                u.setPassword(rs.getString("Password"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
            }
            rs.close();
            statement.close();
            conn.close();
            
        } catch(SQLException e) {
            System.out.println(e);
        }
        return u;
    }
    
    //method for adding a new User to the user table.
    protected static boolean addUser(User user) {
        boolean outcome = false;
        String query
                = "INSERT INTO user "
                + "(FirstName, LastName, Email, Password) "
                + "VALUES ("
                + "\'"
                + user.getFirstName() + "\'" + ", "
                + "\'"
                + user.getLastName() + "\'" + ", "
                + "\'"
                + user.getEmail() + "\'" + ", "
                + "\'"
                + user.getPassword() + "\'" + ")";
        Connection conn = hw6connection();
        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);
            if(result > 0) {
                outcome = true;
            }
            statement.close();
            conn.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        return outcome;
    }
    
    
    //all requests to the servlet are done via POST.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext sc = getServletContext();
        HttpSession session = request.getSession();
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "cart";  // default action
        }
        
        // perform action and set URL to appropriate page
        String url = "/list.jsp";
        switch (action) {
            case "shop":
                url = "/list.jsp";    // the book list page
                session.setAttribute("list", getBookList());
                break;
            case "cart":
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                }
                
                String title = request.getParameter("title");
                Book book = selectBook(title);
                
                String quantityString = request.getParameter("quantity");
                //if the user enters a negative or invalid quantity,
                //the quantity is automatically reset to 1.
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity < 0) {
                        quantity = 1;
                    }
                } catch (NumberFormatException nfe) {
                    quantity = 1;
                }
                
                Line lineItem = new Line();
                lineItem.setBook(book);
                lineItem.setQuantity(quantity);
                if (quantity > 0) {
                    cart.addItem(lineItem);
                } else if (quantity == 0) {
                    cart.removeItem(lineItem);
                }
                
                session.setAttribute("cart", cart);
                
                url="/mycart.jsp";
                break;
            
            case "login":
                session.setAttribute("message", "");
                url = "/signin.jsp";
                break;
            case "check_user":
                String email = request.getParameter("email");
                String pass = request.getParameter("password");
                //check if the user is already registered.
                if(userExists(email)) {
                    //check if the password is correct.
                    if(passwordCorrect(email, pass)) {
                        session.setAttribute("user", selectUser(email));
                        url = "/thanks.jsp";
                    }
                    //if not, tell the user the password is incorrect
                    else {
                        String wrong = "The password does not match. Please try again.";
                        session.setAttribute("message", wrong);
                        url="/signin.jsp";
                    }
                }
                //if not, make the new user register.
                else {
                    User u = new User();
                    u.setEmail(email);
                    u.setPassword(pass);
                    session.setAttribute("user", u);
                    url = "/register.jsp";
                }
                break;
            case "register":
                String first = request.getParameter("first");
                String last = request.getParameter("last");
                User u = (User) session.getAttribute("user");
                u.setFirstName(first);
                u.setLastName(last);
                if(addUser(u)) {
                    session.setAttribute("user", u);
                    url = "/thanks.jsp";
                }
                else {
                    String bad = "Unable to register new user. Try again.";
                    session.setAttribute("message", bad);
                    url="/signin.jsp";
                }
                break;
            case "reset":
                session.invalidate();
                session = request.getSession();
                session.setAttribute("cart", new Cart());
                session.setAttribute("list", getBookList());
                url = "/list.jsp";
                break;
            default:
                break;
        }

        sc.getRequestDispatcher(url)
                    .forward(request, response);
    }



}
