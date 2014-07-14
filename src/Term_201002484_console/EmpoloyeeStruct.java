package Term_201002484_console;
/**
* @see empoloyee
* @version 0.10
* @author Lee, Han-Wool (kudnya@gmail.com)
*/
public class EmpoloyeeStruct {
	private String passwWd, name, phone, address, deposite;
	private int position, ID;
	private long monthPay;
	private EmpoloyeeStruct next;
	/**
	 * 기본 생성자.
	 */
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
	/**
	 * 사원을 추가할 때에 쓰는 생성자
	 * @param passWd 비밀번호
	 * @param name 이름
	 * @param phone 전화번호
	 * @param address 주소
	 * @param deposite 계좌번호
	 * @param position 직급(0~5 작을 수록 직급이 높음)
	 */
	public EmpoloyeeStruct(String passWd, String name, String phone, String address, String deposite, int position){
		this.passwWd = passWd;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.next = null;
		this.deposite = deposite;
		this.position = position;
	}
	/**
	 * 특정 사원의 급여정보를 불러올 때 쓰는 생성자
	 * @param name 이름
	 * @param phone 전화번호
	 * @param address 주소
	 * @param deposite 계좌번호
	 * @param ID 사원번호
	 * @param monthPay 급여
	 */
	public EmpoloyeeStruct(String name, String phone, String address, String deposite, int ID, long monthPay){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.next = null;
		this.deposite = deposite;
		this.ID = ID;
		this.monthPay = monthPay;
	}
	/**
	 * 기타 상황에서 쓰는 생성자
	 * @param name 이름
	 * @param phone 전화번호 
	 * @param address 주소 
	 * @param deposite 계좌번호 
	 * @param ID 사원번호 
	 * @param position 직급
	 * @param onWork 근무여부
	 */
	public EmpoloyeeStruct(String name, String phone, String address, String deposite, int ID, int position, boolean onWork){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.next = null;
		this.deposite = deposite;
		this.ID = ID;
		this.position = position;
		this.onWork = onWork;
	}
	/**
	 * 여러 사원의 정보를 추가할 때 쓰는 링크드리스트 추가용 메소드
	 * @param passWd 비밀번호 
	 * @param name 이름
	 * @param phone 전화번호
	 * @param address 주소
	 * @param deposite 계좌번호
	 * @param position 직급
	 */
	public void addEmpoloyee(String passWd, String name, String phone, String address, String deposite, int position){
		this.next = new EmpoloyeeStruct(passWd, name, phone, address, deposite, position);
	}
	/**
	 * 여러 사원의 급여를 불러올 때 쓰는 링크드리스트 추가용 메소드
	 * @param name 이름 
	 * @param phone 전화번호 
	 * @param address 주소 
	 * @param deposite 계좌번호 
	 * @param ID 사원번호 
	 * @param monthPay 급여
	 */
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
	}	/**
	 * 
	 * @return
	 */
	public String getDeposite() {
		return deposite;
	}

	public void setDeposite(String deposite) {
		this.deposite = deposite;
	}
	
}
