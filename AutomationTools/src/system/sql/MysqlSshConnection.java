package system.sql;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class MysqlSshConnection 
{
	private java.sql.Connection  connect = null;
	
	private final static String 
	sshHost = "dev.something.com",
	sshUsername = "vetal", 
	sshPassword = "password",
	
	dbHost = "localhost", 
	dbUsername = "vetal", 
	dbPassword =  "password",
	dbName = "mirrorserver";
	
    private final static int 
    dbPort = 3306,
    sshPort = getAvailableLocalPort();
    
    
    
	public Connection getConnection() throws SQLException, IOException
	{
		return java.sql.DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + sshPort + "/" + dbName, 
				dbUsername, dbPassword);
	}
	
	public void closeConection()
	{		
		try 
		{
			if(connect != null)
				connect.close();
			connect = null;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		System.out.println("Connection Closed!");
	}
	
	
	/**
     * Gets an available local port by binding to port 0.
     * The OS will allocate the next available port.
     * @throws IOException 
     */
    private static int getAvailableLocalPort() 
    {
        ServerSocket socket;
        int port = 0;
		try 
		{
			socket = new ServerSocket(0);
			port = socket.getLocalPort();
			socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        return port;
    }
    
    /**
     * Creates SSH tunnel 
     * @return
     */
	public Session sshTunnel() 
	{
		Session sshSession = null;
		// SSH Tunnel
		try 
	    {
	        final JSch jsch = new JSch();
	        sshSession = jsch.getSession(sshUsername, sshHost, 22);
	        final Hashtable<String, String> config = new Hashtable<String, String>();
	        config.put("StrictHostKeyChecking", "no");
	        sshSession.setConfig(config);
	        sshSession.setPassword(sshPassword);
	
	        sshSession.connect();
	
	        sshSession.setPortForwardingL(sshPort, dbHost, dbPort);
	
	        return sshSession;
	    } 
	    catch (Exception e) 
	    {
	        throw new RuntimeException("Unable to open SSH tunnel", e);
	    }
	} 
	
	/**
	 * Get result set from query
	 * @param query
	 * @return
	 */
	public ResultSet getResponce(String query)
	{
		Statement stmt;
		ResultSet rs = null;
		
		//create session
		Session session = sshTunnel();
		
		Connection connect;
		try 
		{
			connect = getConnection();
			stmt = connect.createStatement();
			rs = stmt.executeQuery(query);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//close connection
		closeConection();
		//disconnect session
		session.disconnect();
		
		return rs;
	}
}
