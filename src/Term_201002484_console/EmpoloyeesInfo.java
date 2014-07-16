package Term_201002484_console;
/**
* @version 0.10
* @author Lee, Han-Wool (kudnya@gmail.com)
*/
public class EmpoloyeesInfo {
	private EmpoloyeesInfo next;
	private empoloyee empoloyees;
	private String passWd;
	/**
	 * 기본 생성자.
	 */
	public EmpoloyeesInfo() {
		// TODO Auto-generated constructor stub
		this.next = null;
		this.empoloyees = null;
	}
	/**
	 * 사원을 추가할 때에 쓰는 생성자
	 * @param passWd 비밀번호
	 * @param empoloyees 직원 객체
	 */
	public EmpoloyeesInfo(empoloyee empoloyees, String passWd){
		this.passWd = passWd;
		this.next = null;		
		this.empoloyees = empoloyees;
	}
	/**
	 * 특정 사원의 급여정보를 불러올 때 쓰는 생성자
	 * @param passWd 비밀번호
	 * @param empoloyees 직원 객체
	 */
	public EmpoloyeesInfo(empoloyee empoloyees){
		this.passWd = null;
		this.next = null;		
		this.empoloyees = empoloyees;
	}
	/**
	 * 여러 사원의 정보를 추가할 때 쓰는 링크드리스트 추가용 메소드
	 * @param passWd 비밀번호 
	 * @param newstaff 직원 객체 
	 */
	public void addEmpoloyee(String passWd, empoloyee newstaff){
		this.next = new EmpoloyeesInfo(newstaff, passWd);
	}
	/**
	 * 여러 사원의 급여를 불러올 때 쓰는 링크드리스트 추가용 메소드
	 * @param newstaff 직원 객체
	 */
	public void addEmpoloyee(empoloyee newstaff){
		this.next = new EmpoloyeesInfo(newstaff);
	}
	public EmpoloyeesInfo getNext() {
		return next;
	}
	public void setNext(EmpoloyeesInfo next) {
		this.next = next;
	}
	public empoloyee getEmpoloyees() {
		return empoloyees;
	}
	public void setEmpoloyees(empoloyee empoloyees) {
		this.empoloyees = empoloyees;
	}
	public String getPassWd() {
		return passWd;
	}
}
