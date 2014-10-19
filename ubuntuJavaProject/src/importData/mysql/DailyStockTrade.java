/** Daily stock of TW
 * 
 */
package importData.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * @author msDev
 *
 */
public class DailyStockTrade {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		System.out.println("ImportData.MySQL begin .....");
		//1.READ CSV
		//2.OPEN CONN
		//3.INSERT DATA
		String Dat = "";//"20140523";
		String date = "";
		
		String inputFolderPath = "D:\\stock\\Data\\TW\\volume";
		boolean rtStatus = false;

		// open connection
		Connection conn = null;
	    Class.forName("com.mysql.jdbc.Driver"); 
	    conn = DriverManager.getConnection( 
	      "jdbc:mysql://192.168.1.20:3306/Stocks?useUnicode=true&characterEncoding=UTF-8", 
	      "stock","inside22");
		
		
		File f = new File(inputFolderPath);
		File[] child = f.listFiles();
		String inputFileFullName = "";		
		String finishedFileFullName = "";
		for (int i=0; i<child.length; i++)
		{
			inputFileFullName = inputFolderPath + "\\" + child[i].getName();
			finishedFileFullName =  inputFolderPath + "\\done\\" + child[i].getName();
			System.out.println("inputFileFullName: " + inputFileFullName);
			if (child[i].getName().trim().length() > 8)
			{
				Dat = child[i].getName().substring(0, 8);
				date = Dat.substring(0, 4) + "-" + Dat.substring(4, 6) + "-" + Dat.substring(6);
			    //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				//Date inDate = (Date) df.parse(date);
				Date inDate = Date.valueOf(date);
				rtStatus = doAction(conn, inputFileFullName, finishedFileFullName, Dat, inDate);
			}
			System.out.println("file: " + inputFileFullName + ", rtStatus: " + rtStatus + ".");
		}
		conn.close();
	}

	
	//Separate string into string arrays
	static String[] getStringArrays(String input)
	{
		String[] rtArr = null;
		if(!"".equals(input))
		{
			rtArr = input.split(",");
		}
		System.out.println("getStringArrays, size: " + rtArr.length);
		for(int i = 0; i < rtArr.length; i++)
		{
			if (i == rtArr.length - 1)
			{
				//System.out.println("rtArr["+ i +"]: " + rtArr[i] + ".");
			}
			else
			{
				//System.out.print("rtArr["+ i +"]: " + rtArr[i] + ",");
			}
		}
		return rtArr;
	}
	
	//find double quote position and eliminate thousand sign
	static String getDoubleQuote(String input)
	{
		String rtStr = "";
		if (!"".equals(input))
		{
			//find the first "
			int i = input.indexOf("\"");
			if (i > -1)
			{
				String head = input.substring(0, i);
				String tail = input.substring(i+1);
				//System.out.println("head: " + head + ", tail: " + tail + ".");
				rtStr = head + tail;
				//find the second "
				int j = tail.indexOf("\"");
				if (j > -1)
				{
					String middle = tail.substring(0, j).replaceAll(",","");
					tail = middle + tail.substring(j+1, tail.length());
					//System.out.println("tail: " + tail + ".");
					rtStr = getDoubleQuote(head + tail);
				}				
			}
			else 
			{
				rtStr = input;
				//System.out.println("input: " + input + ".");
			}
		}
		return rtStr;
	}
	//Insert data into MySql
	static boolean doInsertData(Connection conn, String[] input, String Dat, Date inDate)
	{
		boolean rtStatus = false;
		try
		{
			String sql = "INSERT INTO Stocks.I_T_DAILYSTOCKVOLUME VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
			PreparedStatement DbStmt = null;
			DbStmt = conn.prepareStatement(sql);
 
            DbStmt.setString(1,Dat);
            DbStmt.setString(2,input[0]);
            DbStmt.setString(3,input[1]);
            DbStmt.setLong(4,Long.parseLong(input[2]));
            DbStmt.setLong(5,Long.parseLong(input[3]));
            DbStmt.setLong(6,Long.parseLong(input[4]));
            DbStmt.setLong(7,Long.parseLong(input[5]));           
            DbStmt.setLong(8,Long.parseLong(input[6]));
            DbStmt.setLong(9,Long.parseLong(input[7]));
            DbStmt.setLong(10,Long.parseLong(input[8]));
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
	
	//action in single file
	static boolean doAction(Connection conn, String inputFileName, String finishedFileName, String Dat, Date inDate)
	{
		boolean rtStatus=false;
		//STEP 1.
		//String inputFileFullName = "C:\\Users\\msDev\\Downloads\\20140523_2by_issue.csv";
		File fd = new File(inputFileName);
		//Step 2's function
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fd),"big5"));
			String s;
			int il = 0;
			while((s = br.readLine()) != null)
			{
				System.out.println(il + "," + s);
				if ("".equals(s.trim()))
				{
					break;
				}
				String str = getDoubleQuote(s.trim());
				String[] strAr = getStringArrays(str);
				if ((strAr.length == 9) && (!"�Ҩ�N��".equals(strAr[0])))
				{
					//Step 3's Function
					boolean srtStatus = doInsertData(conn, strAr, Dat, inDate);
				}
				il += 1;
			}
			br.close(); 
			//move file
			File fdnew = new File(finishedFileName);
			if (fd.renameTo(fdnew))
			{
				System.out.println("move file sucess -- " + finishedFileName + ".");
			} 
			else 
			{
				System.out.println("move file fail -- " + finishedFileName + ".");
			}				
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}				
		return rtStatus;
	}
}