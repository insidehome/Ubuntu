/**
 * 
 */
package importData.mssql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utility.DbConnection;
import utility.strUtility;
/**
 * @author CHAO
 * DATE:20141011
 * PURPOSE:IMPORT DATA FROM WEB'S DOWNLOADED FILE(CSV)
 * TARGET: I_T_FUTURE_DAILY_PRICE (���f�C�����污)
 *
 */
public class I_T_FUTURE_DAILY_PRICE {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub		
		String strDownloadFile = 
				"C:\\Users\\chao\\Downloads\\950C1ABB-19A2-406A-A8CA-B29A00429958.CSV";	
		//1.GET FILE
		//2.SPILT DATA 
		//3.IMPORT DATA	
		//System.out.println("args.length: " + args.length + ".");
		if (args.length>0)
		{
			strDownloadFile = args[0].toString();
		}
		System.out.println("The download file is : " + strDownloadFile + ".");
		
		DbConnection cn = null;
		Connection con = null;
		PreparedStatement DbStmt = null;
		File f = null;
		BufferedReader br = null;
		try{
				
			//test db
			cn = new DbConnection("Azure");
			con = cn.open();
			//insert sql script
			String sql = "";
			//1
			//directory
			f = new File(strDownloadFile);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"big5"));
			
			String strSingleLine = "";
			int row = 0;
			int fixColumn = 0;
			
			while ((strSingleLine=br.readLine()) != null)
			{				
				row+=1;
				//DO NOTHING IN FIRST 1 ROW
				if (row>1)
				{
					//System.out.println(row + ": " + strSingleLine + ".");
					System.out.println(row + ": fixColumn: " + fixColumn +".");
					String[] strArr = new strUtility().getStringArrays(strSingleLine);

					if (row ==2)
					{
						fixColumn=strArr.length;
						sql = new strUtility().getSqlInsertCommand("[dbo].[I_T_FUTURE_DAILY_PRICE]", fixColumn-1);
						DbStmt = con.prepareStatement(sql);
						System.out.println("fixColumn: " + fixColumn + ".");
						System.out.println("sql: " + sql + ".");
					}	
					
					if ((row >= 2) && (fixColumn == strArr.length)) 
					{	
						//custermization of input data
						//1-traddate, productid, targetmonth, openp, highp. lowp, closep, diff, -8 
						//9-volume, final, unfisihedvolume, bestbuy, bestsell, highest, lowest  -15
 						DbStmt.setString(1,new strUtility().getYMDString(strArr[0]));
			            DbStmt.setString(2,strArr[1].trim());
			            DbStmt.setString(3,strArr[2].trim());
			            DbStmt.setDouble(4, Double.parseDouble(strArr[3].trim()));
			            DbStmt.setDouble(5, Double.parseDouble(strArr[4].trim()));
			            DbStmt.setDouble(6, Double.parseDouble(strArr[5].trim()));
			            DbStmt.setDouble(7, Double.parseDouble(strArr[6].trim()));
			            DbStmt.setDouble(8, Double.parseDouble(new strUtility().getDiffData(strArr[7].trim())));
			            DbStmt.setDouble(9, Double.parseDouble(strArr[9].trim()));
			            DbStmt.setDouble(10, Double.parseDouble(strArr[10].trim()));
			            DbStmt.setDouble(11, Double.parseDouble(strArr[11].trim()));
			            DbStmt.setDouble(12, Double.parseDouble(strArr[12].trim()));
			            DbStmt.setDouble(13, Double.parseDouble(strArr[13].trim()));
			            DbStmt.setDouble(14, Double.parseDouble(strArr[14].trim()));
			            DbStmt.setDouble(15, Double.parseDouble(strArr[15].trim()));
			            int x = DbStmt.executeUpdate();
			            System.out.println(row + ": " + x + ".");
					}
					else  
					{
						System.out.println(row + ": This is something wrong in the row!");
					}
				}
			}
			
			br.close();
			DbStmt.close();
			cn.close(con);
			if (DbStmt != null) try { DbStmt.close(); } catch(Exception e) {System.out.println(e.getMessage());}
		} 
		catch (Exception e)
		{
			System.out.print(e.getMessage());
		}		
		finally
		{
			if (br!=null) br.close();
			if (DbStmt != null) DbStmt.close();
			if (con != null) cn.close(con);
		}
		
	}
}