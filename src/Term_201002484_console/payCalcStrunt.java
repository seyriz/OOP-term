package Term_201002484_console;
import java.io.*;

public class payCalcStrunt {
	private dbCon DB;
	private int ID, Month, Year;
	private String name, phone, deposite;
	public payCalcStrunt() {
		// TODO Auto-generated constructor stub
	}
			
	public payCalcStrunt(int ID, int Year, int Month){
		this.DB = new dbCon();
		this.ID = ID;
		this.Year = Year;
		this.Month = Month;
	}
	
	public long personalPayCalc(int ID, int Year, int Month){
		return DB.getMonthPay(ID, YYYYMM);
	}
	
}
