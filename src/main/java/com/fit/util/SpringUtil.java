package com.fit.util;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SpringUtil {
	/**
	 * 
	 * @param splitStr 分割的字符串
	 * @param len 分割的长度
	 * @return
	 */
	public static List<String> splitByLen(String splitStr,int len){
		List<String> ret = new ArrayList<String>();
		int end = len;  
		String s;
		boolean flag = true;
		for(int start=0; start<=splitStr.length() && flag;)
		{  
		    if(end>splitStr.length()){  
		        end = splitStr.length();
				flag = false;
		    }  
		    s = splitStr.substring(start, end);  
		    start = end ;  
		    end = end + len ;  
		    System.out.print(s+"\t");  
		    ret.add(s);
		}
		return ret;
	}
	
	/**
	 * 将十六进制对象转换成十进制
	 * @param hexObject
	 * @return
	 */
	public static Object hexToDecimal(Object hexObject){
		long lDecimal;
		if(hexObject instanceof List){
			List<?> objectList = (List<?>)hexObject;
			List<String> temp = new ArrayList<String>();
			for(int i=0; i<objectList.size();i++){
				lDecimal = Long.parseLong((String) objectList.get(i),16);
				temp.add(Long.toString(lDecimal));
			}
			objectList = null;
			System.out.print(temp.toString());
			return temp;
			
		}
		lDecimal = Long.parseLong((String)hexObject,16);
	    return Long.toString(lDecimal);
	}
	

	
	public static void main(String[] args) throws ParseException{
		
		List<String> list = SpringUtil.splitByLen("000200020002000200020002000200020002000200", 4);
		Object temp =  SpringUtil.hexToDecimal(list);
	    System.out.print(temp);  

	}
}
