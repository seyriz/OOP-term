package Term_201002484_console;
import java.io.*;

import au.com.bytecode.opencsv.CSVReader;
/**
* IO 프로세싱 인터페이스
* @version 0.10
* @author Lee, Han-Wool (kudnya@gmail.com)
*/
public interface IOProc {
	/**
	 * 개인의 한달 급여를 출력.
	 * @param ID 사원번호
	 * @param YYYYMM 연월(YYYYMM형식)
	 */
	public void printPayBill(int ID, int YYYYMM);
	public void printPaySheet();
	public EmpoloyeesInfo importEmpoloyee(String filePath);
}
