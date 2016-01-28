import java.net.*;
import java.io.*;
import java.sql.*;


public class Server extends Thread
{
    protected Socket clientSocket;

    public static void main(String[] args) throws IOException
    {

        ServerSocket server = null;


        // Load MySQL driver.
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        try {
            server = new ServerSocket(10007);
            System.out.println ("Connection Socket Created");
            try {
                while (true)
                {
                    System.out.println ("Waiting for Connection");
                    new Server (server.accept());
                }
            }
            catch (IOException e)
            {
                System.err.println("fail");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Error binding to port");
            System.exit(1);
        }
        finally
        {
            try {
                server.close();
            }
            catch (IOException e)
            {
                System.err.println("ERROR.");
                System.exit(1);
            }
        }
    }


    private Server (Socket clientSoc)
    {
        clientSocket = clientSoc;
        start();
    }

    public void run()
    {
        System.out.println ("Thread startet");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println();
                System.out.println ("Server: " + inputLine);

                if(inputLine.contains("getmail")) {

                    out.println(parser(inputLine,false));

                }

                else if (inputLine.contains("getphone")) {

                    out.println(parser(inputLine,true));

                }

                else {

                    out.println("getphone name lastname OR getmail name lastname");

                }

                if (inputLine.equals("quit"))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("ERROR");
            System.exit(1);
        }
    }

    public static String parser (String input, boolean phone) {

        String[] split = input.split(" ");
        String result = "";
        try {
            String name = split[1];
            String lname = split[2];

            System.out.println(name + " " + lname);

            result = getEmail(name,lname,phone);

        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Fy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getEmail (String q_name, String q_lastname, boolean phone) throws SQLException {

        Connection  conn = DriverManager.getConnection("jdbc:mysql://localhost/bigdata", "bigdata", "bigdata2016");
        ResultSet rs;
        Statement stmt = conn.createStatement() ;
        String result;
        String sql = "SELECT * FROM bigdata.socket_demo WHERE name='"+ q_name +"' AND lname='"+ q_lastname +"'";

        stmt = conn.createStatement() ;
        rs = stmt.executeQuery(sql);

        rs.next();

        if(phone) {
            result = "Phone : " + rs.getString("phone");
        }
        else {
            result = "Email : " + rs.getString("email");
        }


        stmt.close();
        return result;
    }







}

