package utility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utility.strUtility;

public class SqlCommand {
	//Insert data 
	static boolean doInsertData(Connection conn, String stTBName, String[] input, String Dat, Date inDate)
	{
		boolean rtStatus = false;
		try
		{
			if ("".equals(stTBName))
			{
				System.out.println("no Table Name");
				return rtStatus;
			}
			
			//command script
			String sql = new strUtility().getSqlInsertCommand(stTBName, input.length); 
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);
			//input data	
            DbStmt.setString(1,Dat);
            DbStmt.setString(2,input[0]);
            DbStmt.setString(3,input[1]);
            DbStmt.setInt(4,Integer.parseInt(input[2]));
            DbStmt.setInt(5,Integer.parseInt(input[3]));
            DbStmt.setInt(6,Integer.parseInt(input[4]));
            DbStmt.setInt(7,Integer.parseInt(input[5]));           
            DbStmt.setInt(8,Integer.parseInt(input[6]));
            DbStmt.setInt(9,Integer.parseInt(input[7]));
            DbStmt.setInt(10,Integer.parseInt(input[8]));
            DbStmt.setDate(11,inDate);
            DbStmt.executeUpdate();
            if (DbStmt != null) try { DbStmt.close(); } catch(Exception e) {}
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return rtStatus;
	}	
	//truncate table 
	static boolean doTruncateData(Connection conn, String stTBName)
	{
		boolean rtStatus = false;
		try
		{
			if ("".equals(stTBName))
			{
				System.out.println("no Table Name");
				return rtStatus;
			}
			
			//command script
			String sql = new strUtility().getSqlTruncateCommand(stTBName); 
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);

            DbStmt.executeUpdate();
            if (DbStmt != null) try { DbStmt.close(); } catch(Exception e) {}
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return rtStatus;
	}	
}