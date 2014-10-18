package importData.mysql;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class DailyStockFinanceBearish {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		System.out.println("ImportData.MySQL begin .....");
		//1.READ CSV
		//2.OPEN CONN
		//3.INSERT DATA
		String Dat = "";//"20140523";
		String date = "";
		
		String inputFolderPath = "D:\\stock\\Data\\TW\\fb";
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
				Dat = child[i].getName().substring(4, 12);
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
		//System.out.println("getStringArrays, size: " + rtArr.length);
		for(int i = 0; i < rtArr.length; i++)
		{
			if (("--".equals(rtArr[i].trim())) || ("".equals(rtArr[i].trim())))
			{
				rtArr[i] = "0";
			}
			if (i == rtArr.length - 1)
			{
				System.out.println("rtArr["+ i +"]: " + rtArr[i] + ".");
			}
			else
			{
				System.out.print("rtArr["+ i +"]: " + rtArr[i] + ",");
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
		PreparedStatement DbStmt = null;
		String str16 = (input.length==15)?"":input[15];
		try
		{
			String sql = "INSERT INTO Stocks.I_T_DAILYSTOCKFINANCEBEARISH VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
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
            DbStmt.setLong(11,Long.parseLong(input[9]));
            DbStmt.setLong(12,Long.parseLong(input[10]));            
            DbStmt.setLong(13,Long.parseLong(input[11]));
            DbStmt.setLong(14,Long.parseLong(input[12]));
            DbStmt.setLong(15,Long.parseLong(input[13]));
            DbStmt.setLong(16,Long.parseLong(input[14]));
            DbStmt.setString(17,str16);
            DbStmt.setDate(18,inDate);
            DbStmt.executeUpdate();
            rtStatus = true;
		} 
		catch(SQLException x)
		{
			System.out.println("SQLException: " +x.toString());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (DbStmt != null) try { DbStmt.close(); } catch(Exception e) {}
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
					//break;
					continue;
				} 
				String str = getDoubleQuote(s.trim());
				String[] strAr = getStringArrays(str);
				System.out.println("strAr.length: " + strAr.length + ".");
				if ((strAr.length >= 15) && (!strAr[0].equals("�Ҩ�N��")))
				{
					//if (il <= 450)
					//{
					//System.out.println(il + ", " +  s + ".");
					//Step 3's Function
					boolean srtStatus = doInsertData(conn, strAr, Dat, inDate);
					//}
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
