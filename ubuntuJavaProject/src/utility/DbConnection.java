package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	//private SQL Database
	private final static String dbMySql = "jdbc:mysql://192.168.1.20:3306/Stocks?useUnicode=true&characterEncoding=UTF-8&user=stock&password=inside22";
	private final static String dbAzureMSSql = "jdbc:sqlserver://yyrl8h9r5c.database.windows.net:1433;database=cloudMSDB;user=stocks;password=Inside22";
	private final static String dbMSSql ="jdbc:sqlserver://10.19.10.83:1433;database=Futures;user=sa;password=1234";
	//private final static String dbMongodb = "mongodb://stocks:Inside22@ds037990.mongolab.com:37990/smsdb";
	private final static String dbDB2 = "jdbc:db2://10.19.10.88:50001/FUTURES:user=db2admin;password=db2admin;";
	//private classname of SQL
	private final static String strMySqlCls = "com.mysql.jdbc.Driver";
	private final static String strAzureMSSqlCls = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String strMSSqlCls = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//private final static String strMongodbCls = "";
	private final static String strDb2Cls = "com.ibm.db2.jcc.DB2Driver";
	//action target
	private String initialDB = "";
	private String initialDBCls = ""; 

	//constructor
	public DbConnection(String input)
	{
		System.out.println("initialize DbConnection .. " + input + ".");
		if (!"".equals(input))
		{
			if (input.equals("MySql"))
			{
				initialDB = dbMySql;
				initialDBCls = strMySqlCls;
			}
			else if (input.equals("Azure"))
			{
				initialDB = dbAzureMSSql;
				initialDBCls = strAzureMSSqlCls;
			}
			else if (input.equals("MSSQL"))
			{
				initialDB = dbMSSql;
				initialDBCls = strMSSqlCls;
			}
			//else if (input.equals("Mongodb"))
			//{
			//	initialDB = dbMongodb;
			//	initialDBCls = strMongodbCls;
			//}
			else if (input.equals("Db2"))
			{
				initialDB = dbDB2;
				initialDBCls = strDb2Cls;
			}
		}
		else 
		{
			System.out.println("Which Database do you want?");
		}
		System.out.println("initialDB: " + initialDB + ", initialDBCls: " + initialDBCls + ".");
	}
	
	//open a connection
	public Connection open()
	{
		// open connection
		Connection conn = null;
	    try {
			Class.forName(initialDBCls);
			conn = DriverManager.getConnection(initialDB);
			System.out.println("I am opened now!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("can't find driver class");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("can't talk to DB");
			e.printStackTrace();
		} 	    
		return conn;
	}

	//close connection
	public void close(Connection input)
	{
	    try {
			if (input != null)
			{
				input.close();
				System.out.println("I am closeed now!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("can't talk to DB");
			e.printStackTrace();
		} 	    
	}
}