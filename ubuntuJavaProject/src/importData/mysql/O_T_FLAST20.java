/** DATE:20140526
 *    PURPOSE: TRANSFER DATA INTO O_T_FTOP20
 */
package importData.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author insidehome
 *
 */
public class O_T_FLAST20 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tradedate = "20141231";
		boolean rtStatus = false;
		System.out.println("Begin ......");
		//1.GET DISTINCT TRADE DATE
		//2.GET TOP 20 OF EACH TRADE DATE
		//3.CHECK IF IT EXISTED ON LAST DAY
		//INSERT DATA INTO DB
		try 
		{
			Connection conn = null;
		    Class.forName("com.mysql.jdbc.Driver"); 
		    conn = DriverManager.getConnection( 
		      "jdbc:mysql://192.168.1.20:3306/Stocks?useUnicode=true&characterEncoding=UTF-8", 
		      "stock","inside22");			
		    boolean r = doTruncateData(conn, tradedate);
		    //DISTINC TRADEDATE
		    ArrayList<String> al = doQueryDistinctTradedate(conn, tradedate);
		   for (int i=0; i<al.size(); i++)
		   {   
			    //INSERT TOP 20
			    rtStatus = doInsertTop20(conn, al.get(i));
			    //System.out.println("rtStatus: " + rtStatus + ", tradedate: " + al.get(i) + ".");
		    }
		   for (int i=0; i<al.size(); i++)
		   {  
		    	//System.out.println("tradedate: " + al.get(i)  + ", " + i + ", size: " + al.size() + ".");
		    	if (i < (al.size()-1))
		    	{
		    		//System.out.println("yesterday: " + al.get(i+1) + ", today: " + al.get(i) + ".");
			    	ArrayList<String> yesStock = getTop10(conn, al.get(i+1));
			    	//System.out.println("go to today!");
			    	ArrayList<String> todayStock = getTop10(conn, al.get(i));
			    	boolean isNewBaby = true;
			    	for(String input : todayStock)
			    	{
			    		//System.out.println("todayStock: " + input + ".");
			    		isNewBaby = true;
			    		for(String input1 : yesStock)
			    		{
			    			//System.out.println("todayStock: " + input + ", yesStock: " + input1 + ".");
			    			if (input1.equals(input))
			    			{
			    				isNewBaby = false;
			    				break;
			    			}
			    		}
		    			if (isNewBaby)
		    			{
		    				//System.out.println(input + "' --  isNewBaby: " + isNewBaby + ".");
		    				//update today's stock record's newbaby
		    				rtStatus = doUpdateNewBaby(conn, al.get(i), input);
		    			}
			    	}
		    	}
		    }
		    
			conn.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//get distinct tradedate
	static ArrayList<String> doQueryDistinctTradedate(Connection conn, String tradedate)
	{
		ArrayList<String> rtAL = new ArrayList<String>();
		try
		{

			String sql = "SELECT DISTINCT TRADEDATE FROM I_T_DAILYSTOCKVOLUME " +
			"WHERE TRADEDATE <= '" + tradedate + "' ORDER BY TRADEDATE DESC ";			
			Statement stmt = conn.createStatement();		
			ResultSet rs = stmt.executeQuery(sql);				
			while (rs.next())
			{
				//System.out.println("TradeDate: " + rs.getString("TRADEDATE") + ".");
				String TDATE = rs.getString("TRADEDATE");
				rtAL.add(rs.getString("TRADEDATE"));
			}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            System.out.println("doQueryDistinctTradedate done .....");
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return rtAL;
	}
	
	//TOP 20 Insert data into MySql
	static boolean doInsertTop20(Connection conn, String tradedate)
	{
		boolean rtStatus = false;
		try
		{
			String sql = 
			"INSERT INTO Stocks.O_T_FLAST20 (TRADEDATE, TDATE, STOCKID, STOCKNAME, BUYIN, SELLOUT, TOTAL)" +
			"SELECT A.TRADEDATE, A.TDATE, A.STOCKID, A.STOCKNAME, A.F_BUYIN, A.F_SELLOUT, (A.F_BUYIN - A.F_SELLOUT) AS TOTAL " +
			"FROM Stocks.I_T_DAILYSTOCKVOLUME A " +
			"WHERE A.TRADEDATE = ? " +
			"ORDER BY (A.F_BUYIN - A.F_SELLOUT) " +
			"LIMIT 20 ";
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);
 
            DbStmt.setString(1,tradedate);
            DbStmt.executeUpdate();
            rtStatus = true;
            if (DbStmt != null) try { DbStmt.close(); } catch(Exception e) {}
            System.out.println("doInsertTop20 done .....");
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
	//GET YESTERDAY TOP 10 STOCKID
	static ArrayList<String> getYesterdayTop10(Connection conn, String tradedate)
	{
		ArrayList<String> rtAL = new ArrayList<String>();
		try
		{
			String sql = "SELECT A.STOCKID FROM Stocks.O_T_FLAST20 A " +
			"WHERE A.TRADEDATE = '" + tradedate + "' ORDER BY A.TOTAL LIMIT 10";			
			Statement stmt = conn.createStatement();		
			ResultSet rs = stmt.executeQuery(sql);				
			while (rs.next())
			{
				//System.out.println("STOCKID: " + rs.getString("STOCKID") + ".");
				String STOCKID = rs.getString("STOCKID");
				rtAL.add(rs.getString("STOCKID"));
			}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return rtAL;		
	}
	//GET TOP 10 OF TRADEDATE STOCKID
	static ArrayList<String> getTop10(Connection conn, String tradedate)
	{
		//System.out.println("getTop10: " + tradedate + ".");
		ArrayList<String> rtAL = new ArrayList<String>();
		try
		{
			String sql = "SELECT A.STOCKID FROM Stocks.O_T_FLAST20 A " +
			"WHERE A.TRADEDATE = '" + tradedate + "' ORDER BY A.TOTAL LIMIT 10";			
			Statement stmt = conn.createStatement();		
			ResultSet rs = stmt.executeQuery(sql);				
			while (rs.next())
			{
				//System.out.println(tradedate + ", STOCKID: " + rs.getString("STOCKID") + ".");
				String STOCKID = rs.getString("STOCKID");
				rtAL.add(rs.getString("STOCKID"));
			}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return rtAL;		
	}	
	//update data for new baby
	static boolean doUpdateNewBaby(Connection conn, String tradedate, String stockid)
	{
		boolean rtStatus = false;
		try
		{
			String sql = 
			"UPDATE Stocks.O_T_FLAST20 SET NEWBABY = 'Y' " +
			"WHERE TRADEDATE = ?  AND STOCKID = ? ";
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);
 
            DbStmt.setString(1,tradedate);
            DbStmt.setString(2,stockid);
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
	//truncate data for T_FLAST20
	static boolean doTruncateData(Connection conn, String tradedate)
	{
		boolean rtStatus = false;
		try
		{
			String sql = 
			"TRUNCATE Stocks.O_T_FLAST20";
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);
 
            //DbStmt.setString(1,tradedate);
            //DbStmt.setString(2,stockid);
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
