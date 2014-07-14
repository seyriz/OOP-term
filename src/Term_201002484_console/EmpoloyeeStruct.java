package Term_201002484_console;

public class EmpoloyeeStruct {
	private String passwWd, name, phone, address, deposite;
	private int position, ID;
	private long monthPay;
	public String getDeposite() {
		return deposite;
	}

	public void setDeposite(String deposite) {
		this.deposite = deposite;
	}

	private EmpoloyeeStruct next;
	
	public EmpoloyeeStruct() {
		// TODO Auto-generated constructor stub
		this.passwWd = null;
		this.name = null;
		this.phone = null;
		this.address = null;
		this.next = null;
		this.deposite = null;
		this.position = 5;
	}
	
	public EmpoloyeeStruct(String passWd, String name, String phone, String address, String deposite, int position){
		this.passwWd = passWd;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.next = null;
		this.deposite = deposite;
		this.position = position;
	}
	public EmpoloyeeStruct(String name, String phone, String address, String deposite, int ID, long monthPay){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.next = null;
		this.deposite = deposite;
		this.ID = ID;
		this.monthPay = monthPay;
	}
	
	public void addEmpoloyee(String passWd, String name, String phone, String address, String deposite, int position){
		this.next = new EmpoloyeeStruct(passWd, name, phone, address, deposite, position);
	}
	public void addEmpoloyee(String name, String phone, String address, String deposite, int ID, long monthPay){
		this.next = new EmpoloyeeStruct(name, phone, address, deposite, ID, monthPay);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPasswWd() {
		return passwWd;
	}

	public void setPasswWd(String passwWd) {
		this.passwWd = passwWd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EmpoloyeeStruct getNext() {
		return next;
	}

	public void setNext(EmpoloyeeStruct next) {
		this.next = next;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public long getMonthPay() {
		return monthPay;
	}

	public void setMonthPay(long monthPay) {
		this.monthPay = monthPay;
	}
	

}
