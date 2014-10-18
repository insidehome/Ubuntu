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
//�C��d�B�����ƶפJ
public class DailyStockBigDealTrade {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		System.out.println("ImportData.MySQL begin .....");
		//1.READ CSV
		//2.OPEN CONN
		//3.INSERT DATA
		//String Dat = "";//"20140523";
		//String date = "";
		
		String inputFolderPath = "D:\\stock\\Data\\TW\\bigdeal";
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
				//Dat = child[i].getName().substring(4, 12);
				//date = Dat.substring(0, 4) + "-" + Dat.substring(4, 6) + "-" + Dat.substring(6);
			    //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				//Date inDate = (Date) df.parse(date);
				//Date inDate = Date.valueOf(date);
				rtStatus = doAction(conn, inputFileFullName, finishedFileFullName);
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
	static boolean doInsertData(Connection conn, String[] input, String Dat, Date inDate, int rowid)
	{
		boolean rtStatus = false;
		PreparedStatement DbStmt = null;
		try
		{
			String sql = "INSERT INTO Stocks.I_T_DAILYSTOCKBIGDEALTRADE VALUES(?,?,?,?,?,?,?,?,?) ";
			DbStmt = conn.prepareStatement(sql);
 
            DbStmt.setString(1,Dat);
            DbStmt.setInt(2, rowid);
            DbStmt.setString(3,input[0]);
            DbStmt.setString(4,input[1]);
            DbStmt.setString(5,input[2]);
            DbStmt.setDouble(6,Double.parseDouble(input[3]));
            DbStmt.setDouble(7,Double.parseDouble(input[4]));
            DbStmt.setDouble(8,Double.parseDouble(input[5]));
            DbStmt.setDate(9,inDate);
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
	static boolean doAction(Connection conn, String inputFileName, String finishedFileName)
	{
		boolean rtStatus=false;
		//STEP 1.
		//String inputFileFullName = "C:\\Users\\msDev\\Downloads\\20140523_2by_issue.csv";
		File fd = new File(inputFileName);
		//Step 2's function
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fd),"big5"));
			String s = "";
			String dat = "";
			String date = "";
			Date inDate=null;;
			int il = 0;
			while((s = br.readLine()) != null)
			{
				//System.out.println(il + "," + s);
				if ("".equals(s.trim()))
				{
					break;
				} 
				if (il==0)
				{
					dat = getDateFromFirstLine(s.trim());
					date = dat.substring(0, 4) + "-" + dat.substring(4, 6) + "-" + dat.substring(6);
					inDate = Date.valueOf(date);
					il += 1;
					continue;
				}
				String str = getDoubleQuote(s.trim());
				String[] strAr = getStringArrays(str);
				if ((!"".equals(dat)) && (strAr.length == 6) && (!strAr[0].equals("�Ҩ�N��")) && (!strAr[0].equals("�`�p")))
				{
					//if (il <= 450)
					//{
					System.out.println(il + ", " +  s + ".");
					//Step 3's Function
					boolean srtStatus = doInsertData(conn, strAr, dat, inDate, il);
					//}
				}
				il += 1;
			}
			br.close(); 
			//change filename
			finishedFileName = addDateIntoFileName(finishedFileName, dat);
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
	
	//20140824�A��o�Ĥ@�檺�r���{����A103�~08��22��
	static String getDateFromFirstLine(String input)
	{
		System.out.println("getDateFromFirstLine begin .....");
		String rtStr = "";
		if (!"".equals(input))
		{
			String finalDT = "";
			String[] dt = {"�~","��","��"};
			int y=0;
			int m=0;
			int d=0;			
			y = input.indexOf(dt[0]);
			m = input.indexOf(dt[1]);
			d = input.indexOf(dt[2]);
			finalDT = String.valueOf(Integer.parseInt(input.substring(0,y)) + 1911) + input.substring(y+1,m) + input.substring(m+1,d);
			System.out.println("finalDT: " + finalDT + ".");
			rtStr = finalDT;
		}			
		return rtStr;
	}
	//20140824�A�b�ɮק��ڥ[�W���
	static String addDateIntoFileName(String input, String dat)
	{
		System.out.println("getDateFromFirstLine begin .....");
		String rtStr = "";
		if ((!"".equals(input)) && (!"".equals(dat)))
		{
			int dot = input.indexOf(".");
			rtStr = input.substring(0, dot) + dat + input.substring(dot);
			System.out.println("rtStr: " + rtStr);
		}
		return rtStr;
	}
}
