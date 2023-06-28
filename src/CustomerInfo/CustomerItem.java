package CustomerInfo;

public class CustomerItem {

	private int num; // 회원번호
	private String name; // 회원이름
	private String phone; // 회원전화번호
	private String email; // 이메일주소
	private String address; // 사는곳

	public CustomerItem() { // 기본생성자
	}

	public CustomerItem(int num, String name, String phone, String email, String address) { // 생성

		this.num = num;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

	public int getNum() { // getter
		return num;
	}

	public void setNum(int num) { // setter
		this.num = num;
	}

	public String getName() {// getter
		return name;
	}

	public void setName(String name) {// setter
		this.name = name;
	}

	public String getPhone() {// getter
		return phone;
	}

	public void setPhone(String phone) {// setter
		this.phone = phone;
	}

	public String getEmail() {// getter
		return email;
	}

	public void setEmail(String email) {// setter
		this.email = email;
	}

	public String getAddress() {// getter
		return address;
	}

	public void setAddress(String address) {// setter
		this.address = address;
	}
}
