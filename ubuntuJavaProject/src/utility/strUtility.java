package utility;

public class strUtility {

	//get YMD String 20140901 from 2014/9/1
	public String getYMDString(String inStr)
	{
		//System.out.println("getYMDString: " + inStr + ".");
		String rtStr = "";
		if (!"".equals(inStr))
		{
			String[] dateArr = inStr.split("/");
			rtStr = dateArr[0] + getFullDigits(dateArr[1]) + getFullDigits(dateArr[2]);
			//rtStr += "," + getMonthPeriod(dateArr[2]);
		}
		return rtStr;
	}
	//put 0 into single number, 9 -> 09
	public String getFullDigits(String inStr)
	{
		String rtStr = "";
		if (!"".equals(inStr))
		{
			if (inStr.length()==1)
			{
				rtStr = "0" + inStr;
			} else {
				rtStr = inStr;
			}
		}
		return rtStr;
	}	
	//GET 0 if the column is null or '-' 
	public String getNumber(String inStr)
	{
		String rtStr = "";
		if ("".equals(inStr))
		{
			rtStr = "0";
		} else if ("-".equals(inStr))
		{
			rtStr = "0";
		}else {
			rtStr = inStr;
		}
		return rtStr;
	}
	//replace ��, �� to blank
	public String getDiffData(String input)
	{
		String rtStr = "";
		if (!"".equals(input))
		{
			rtStr = input.replace("��", "");
			rtStr = rtStr.replace("��", "");
			System.out.println("input:"+input +", rtStr:"+rtStr+".");
		}
		return rtStr;
	}
	//find double quote position and eliminate thousand sign
	public String getDoubleQuote(String input)
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

	//Separate string into string arrays
	public String[] getStringArrays(String input)
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
	//GENERATE THE INSERT COMMAND SCRIPT
	public String getSqlInsertCommand(String stTBName, int intColumnLength)
	{
		String rtStr = "";
		StringBuffer sb = new StringBuffer("INSERT INTO " + stTBName + " VALUES(");
		
		for(int i=0; i<intColumnLength; i++)
		{
			sb.append("?,");
		}
		
		rtStr = sb.substring(0, sb.length()-1) + ")";
		return rtStr;
	}
	//GENERATE THE TRUNCATE COMMAND SCRIPT
	public String getSqlTruncateCommand(String stTBName)
	{
		String rtStr = "";
		StringBuffer sb = new StringBuffer("TRUNCATE " + stTBName + " ");
		return rtStr;
	}	
	//GET THE TRADETYPE FROM CHINESE INTO CODE
	public String getTradeType(String input)
	{
		String rtStr = "B";
		if ("賣權".equals(input.trim()))
		{
			rtStr = "S";
		}
		else if ("買權".equals(input.trim()))
		{
			rtStr = "B";
		} 
		else 
		{
			rtStr = "N";
		}
		return rtStr;
	}
	//GET THE TRADERID FROM CHINESE INTO CODE
	public String getTraderId(String input)
	{
		String rtStr = "S";
		if ("自營商".equals(input.trim()))
		{
			rtStr = "S";
		}
		else if ("投信".equals(input.trim()))
		{
			rtStr = "T";
		} 
		else if ("外資及陸資".equals(input.trim()))
		{
			rtStr = "F";
		} 
		else 
		{
			rtStr = "N";
		}
		return rtStr;
	}	
}