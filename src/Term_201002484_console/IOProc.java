package Term_201002484_console;
import java.io.*;

import au.com.bytecode.opencsv.CSVReader;

public interface IOProc {
	public void printPayBill(int ID, int YYYYMM);
	public void printPaySheet(int YYYYMM);
	public EmpoloyeeStruct importEmpoloyee(String filePath);
}
