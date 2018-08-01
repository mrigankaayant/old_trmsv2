package com.ayantsoft.trms.jsf.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

public class CalenderUtil implements Serializable {
	
	
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 8230548223550428968L;
	
	
	public TreeMap<Double,Double> incentiveStructure = new TreeMap<Double,Double>();



	public static int getTotalDays(Date dateFromGUI)
	{
		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(dateFromGUI);
		 int year = cal.get(Calendar.YEAR);
		 int month = cal.get(Calendar.MONTH);
		 int day = 1;
		 Calendar mycal = new GregorianCalendar(year,month,day);
		 int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	     return daysInMonth;
	}
	
	
	
	
	public static Date createStartDate(Date dateFromGUI) throws ParseException
	{
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(dateFromGUI);
		 cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		 return cal.getTime(); 	
	}
	
	
	public static Date createEndDate(Date dateFromGUI) throws ParseException
	{
		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(dateFromGUI);
		 cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		 return cal.getTime(); 
	}
	
	
	
	
	public static String monthName(int monthNo)
	{
		if(monthNo == 0)
		{
			return "January";
		}else if(monthNo == 1)
		{
			return "February";
		}else if(monthNo == 2)
		{
			return "March";
		}else if(monthNo == 3)
		{
			return "April";
		}else if(monthNo == 4)
		{
			return "May";
		}else if(monthNo == 5)
		{
			return "June";
		}else if(monthNo == 6)
		{
			return "July";
		}else if(monthNo == 7)
		{
			return "August";
		}else if(monthNo == 8)
		{
			return "September";
		}else if(monthNo == 9)
		{
			return "October";
		}else if(monthNo == 10)
		{
			return "November";
		}else
		{
			return "December";
		}
	}
	
	
	
	
	
	public static double incentiveForIndividual(double incentiveValue)

	{
		if(incentiveValue >= 25000.00)
		{
			return 187500.00;
		}else if(incentiveValue >= 24500.00)
		{
			return 183750.00;
		}else if(incentiveValue >= 24000.00)
		{
			return 180000.00;
		}else if(incentiveValue >= 23500.00)
		{
			return 176250.00;
		}else if(incentiveValue >= 23000.00)
		{
			return 172500.00; 
		}else if(incentiveValue >= 22500.00)
		{
			return 168750.00; 
		}else if(incentiveValue >= 22000.00)
		{
			return 165000.00; 
		}else if(incentiveValue >= 21500.00)
		{
			return 161250.00; 
		}else if(incentiveValue >= 21000.00)
		{
			return 157500.00;
		}else if(incentiveValue >= 20500.00)
		{
			return 153750.00;
		}else if(incentiveValue >= 20000.00)
		{
			return 150000.00; 
		}else if(incentiveValue >= 19500.00)
		{
			return 146250.00; 
		}else if(incentiveValue >= 19000.00)
		{
			return 142500.00;
		}else if(incentiveValue >= 18500.00)
		{
			return 138750.00; 
		}else if(incentiveValue >= 18000.00)
		{
			return 135000.00; 
		}else if(incentiveValue >= 17500.00)
		{
			return 131250.00; 
		}else if(incentiveValue >= 17000.00)
		{
			return 127500.00; 
		}else if(incentiveValue >= 16500.00)
		{
			return 123710.00; 
		}else if(incentiveValue >= 16000.00)
		{
			return 120000.00; 
		}else if(incentiveValue >= 15500.00)
		{
			return 116250.00;
		}else if(incentiveValue >= 15000.00)
		{
			return 112500.00; 
		}else if(incentiveValue >= 14500.00)
		{
			return 108750.00; 
		}else if(incentiveValue >= 14000.00)
		{
			return 105000.00;
		}else if(incentiveValue >= 13500.00)
		{
			return 101250.00; 
		}else if(incentiveValue >= 13000.00)
		{
			return 97500.00; 
		}else if(incentiveValue >= 12500.00)
		{
			return 93750.00; 
		}else if(incentiveValue >= 12000.00)
		{
			return 90000.00; 
		}else if(incentiveValue >= 11500.00)
		{
			return 82800.00; 
		}else if(incentiveValue >= 11000.00)
		{
			return 75900.00; 
		}else if(incentiveValue >= 10500.00)
		{
			return 69300.00; 
		}else if(incentiveValue >= 10000.00)
		{
			return 63000.00; 
		}else if(incentiveValue >= 9500.00)
		{
			return 57000.00; 
		}else if(incentiveValue >= 9000.00)
		{
			return 51300.00;
		}else if(incentiveValue >= 8500.00)
		{
			return 45900.00; 
		}else if(incentiveValue >= 8000.00)
		{
			return 40800.00;
		}else if(incentiveValue >= 7500.00)
		{
			return 36000.00; 
		}else if(incentiveValue >= 7000.00)
		{
			return 31500.00;
		}else if(incentiveValue >= 6500.00)
		{
			return 27300.00;
		}else if(incentiveValue >= 6000.00)
		{
			return 23400.00; 
		}else if(incentiveValue >= 5500.00)
		{
			return 19800.00; 
		}else if(incentiveValue >= 5000.00)
		{
			return 16500.00;
		}else if(incentiveValue >= 4500.00)
		{
			return 13500.00;
		}else if(incentiveValue >= 4000.00)
		{
			return 10800.00; 
		}else if(incentiveValue >= 3500.00)
		{
			return 8400.00; 
		}else if(incentiveValue >= 3000.00)
		{
			return 6300.00;
		}else if(incentiveValue >= 2500.00)
		{
			return 4500.00;
		}else if(incentiveValue >= 2000.00)
		{
			return 3000.00; 
		}else if(incentiveValue >= 1500.00)
		{
			return 1800.00; 
		}else if(incentiveValue >= 1000.00)
		{
			return 900.00; 
		}else{
			return 00.00;
		}
	}
	
	
	
	public static double incentiveForTeamLeader(double incentiveValue)
	{
		if(incentiveValue >= 25000.00)
		{
			return 46875.00;
		}else if(incentiveValue >= 22500.00)
		{
			return 42187.50;
		}else if(incentiveValue >= 20000.00)
		{
			return 37500.00;
		}else if(incentiveValue >= 17500.00)
		{
			return 32812.50;
		}else if(incentiveValue >= 15000.00)
		{
			return 28125.00;
		}else if(incentiveValue >= 12500.00)
		{
			return 23437.50;
		}else if(incentiveValue >= 10000.00)
		{
			return 15750.00;
		}else if(incentiveValue >= 7500.00)
		{
			return 9000.00; 
		}else if(incentiveValue >= 5000.00)
		{
			return 4125.00;
		}else if(incentiveValue >= 2500.00)
		{
			return 1125.00;
		}else
		{
			return 00.00; 
		}
	}

}