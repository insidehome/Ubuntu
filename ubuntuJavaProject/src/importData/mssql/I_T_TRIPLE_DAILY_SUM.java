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
 * @author chao
 * DATE:20141013
 * PURPOSE:IMPORT DATA FROM WEB'S DOWNLOADED FILE(CSV)
 * TARGET: I_T_TRIPLE_DAILY_SUM (�T�j�k�H�����`��)
 */

public class I_T_TRIPLE_DAILY_SUM {
		/**
		 * @param args
		 */
		public static void main(String[] args) throws IOException, SQLException {
			// TODO Auto-generated method stub
			String strDownloadFile = 
					"C:\\Users\\chao\\Downloads\\E7BC6BA0-189E-486C-8C1B-00A756B4CD1B.CSV";	
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
							sql = new strUtility().getSqlInsertCommand("[dbo].[I_T_TRIPLE_DAILY_SUM]", fixColumn);
							DbStmt = con.prepareStatement(sql);
							System.out.println("fixColumn: " + fixColumn + ".");
							System.out.println("sql: " + sql + ".");
						}	
						
						if ((row >= 2) && (fixColumn == strArr.length)) 
						{	
							//custermization of input data
							//1-traddate, traderid, buyvolume, buymoney, sellvolume, sellmoney, finalvolume, finalmoney -8
							//9-unbuyvolume, unbuymoney, unsellvolume, unsellmoney, unfinalvolume, unfinalmoney  -14
	 						DbStmt.setString(1,new strUtility().getYMDString(new strUtility().getDoubleQuote(strArr[0])));
				            DbStmt.setString(2,new strUtility().getTraderId(strArr[1].trim()));
				            DbStmt.setInt(3, Integer.parseInt(strArr[2].trim()));
				            DbStmt.setInt(4, Integer.parseInt(strArr[3].trim()));
				            DbStmt.setInt(5, Integer.parseInt(strArr[4].trim()));
				            DbStmt.setInt(6, Integer.parseInt(strArr[5].trim()));
				            DbStmt.setInt(7, Integer.parseInt(strArr[6].trim()));
				            DbStmt.setInt(8, Integer.parseInt(strArr[7].trim()));
				            DbStmt.setInt(9, Integer.parseInt(strArr[8].trim()));
				            DbStmt.setInt(10, Integer.parseInt(strArr[9].trim()));
				            DbStmt.setInt(11, Integer.parseInt(strArr[10].trim()));
				            DbStmt.setInt(12, Integer.parseInt(strArr[11].trim()));
				            DbStmt.setInt(13, Integer.parseInt(strArr[12].trim()));
				            DbStmt.setInt(14, Integer.parseInt(strArr[13].trim()));
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
	