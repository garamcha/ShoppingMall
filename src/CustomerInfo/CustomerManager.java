package CustomerInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import Fucntion.PrintInfo;

public class CustomerManager implements PrintInfo { // PrintInfo 인터페이스 구현(출력 기능을 수행하는 showInfo()
	// 메소드를 오버라이딩해서 사용함)
	private static CustomerItem c = new CustomerItem(); // 고객정보 클래스
	protected static HashMap<Integer, CustomerItem> hashMap = new HashMap<>(); // 컬렉션(추가, 수정, 삭제, 출력, 검색 기능 담당)
	private static boolean ff = false, result = false;

	public CustomerManager() { // 생성자 : 파일 읽어옴
		readFile();
	}

	@Override
	public void readFile() {
		// TODO Auto-generated method stub
		String path = "customer.csv"; // 읽어들일 csv 파일 이름 지정
		File file = new File(path);
		if (file.exists()) { // 파일이 존재할 때
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strLine = null;
			String[] custArr = null;
			int num = 0;
			try {
				while ((strLine = br.readLine()) != null) { // 반복문: null이 아닐 때까지 한줄씩 읽어들임
					custArr = strLine.split(","); // 읽어들인 코드를 ','단위로 분리함
					try {
						num = Integer.parseInt(custArr[0].trim());// 배열 0번째 요소(고객번호)
						String name = custArr[1].trim();// 배열 1번째 요소(고객이름)
						String phone = custArr[2].trim();// 배열 2번째 요소(고객전화번호)
						String email = custArr[3].trim();// 배열 3번째 요소(이메일주소)
						String address = custArr[4].trim();// 배열 4번째 요소(사는곳)
						addCustomerFile(new CustomerItem(num, name, phone, email, address));// 읽어들인 고객정보를 hashMap에 추가하는
						ff = true;
					} catch (NumberFormatException ex) { // 예외처리
						ex.printStackTrace();
					}
					//System.out.printf("csv정보: %s\n", strLine); // csv 파일로부터 읽어들인 문자열을 출력함
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setNum(num + 1); // 다음에 추가할 고객번호 세팅
		}
	}

	public void addCustomerFile(CustomerItem cItem) {
		hashMap.put(cItem.getNum(), cItem);// void addCustomerFile(): 읽어들인 고객정보를 hashMap에 추가하는 메소드
	}

	public void writerFile() {
		String path = "customer.csv";// 생성할 csv 파일 이름 지정
		File file = new File(path);
		BufferedWriter bw = null;
		try { // 예외처리
			if (file.createNewFile()) {// 새파일을 생성했을 때
				System.out.println("파일 생성됨: " + file.getName()); // 출력코드(파일 생성됨: 파일이름)
			} else { // 파일이 이미 존재할 때
				System.out.println("파일이 이미 존재함"); // 출력코드(파일이 이미 존재함)
			}
			bw = new BufferedWriter(new FileWriter(path, false)); // 파일 쓰기 객체 생성(path명: customer.csv)
			Iterator<Integer> iter = hashMap.keySet().iterator();
			while (iter.hasNext()) {
				int key = iter.next();
				String str; // str: 파일에 한줄 단위로 쓰기 위한 문자열을 저장(',' 구분자 사용)
				str = hashMap.get(key).getNum() + "," + hashMap.get(key).getName() + "," + hashMap.get(key).getPhone()
						+ "," + hashMap.get(key).getEmail() + "," + hashMap.get(key).getAddress() + "\n";
				bw.append(str); // 파일에 쓰기 위해 문자열을 append함
			}

			bw.close(); // 파일 쓰기 객체 종료
		} catch (IOException e) {
			System.out.println("파일 쓰기에 에러 발생!!"); // 출력 코드
			e.printStackTrace();
		}


	}

	public void menu(boolean r) {
		while (r) {
			System.out.println();
			System.out.println("=======고객관리======");
			System.out.println("1. 회원가입");
			System.out.println("2. 회원 정보 수정");
			System.out.println("3. 회원 탈퇴");
			System.out.println("4. 회원 전체 목록 출력");
			System.out.println("5. 회원 검색");
			System.out.println("0. 돌아가기");

			int n = 0;
			try {
				System.out.print("메뉴 선택: ");
				Scanner sc = new Scanner(System.in);
				n = sc.nextInt();
			} catch (InputMismatchException e) {
				e.printStackTrace();
			}

			System.out.println();
			switch (n) {
				case 0:
					if (result == true) {
						writerFile();
					}
					r = false;
					break;
				case 1:
					result = registerCustomer();
					if (result == true) {
						System.out.println("입력 성공");
					} else {
						System.out.println("입력 실패");
					}
					break;
				case 2:
					result = updateCustomer();
					if (result == true) {
						System.out.println("수정 성공");
					} else {
						System.out.println("수정 실패");
					}
					break;
				case 3:
					result = removeCustomer();
					if (result == true) {
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
					}
					break;
				case 4:
					showInfo();
					break;
				case 5:
					searchCustomer();
					break;
				default:
					System.out.println("잘못 입력!!!");
					break;
			}
		}
	}

	public boolean registerCustomer() {
		boolean result = false;
		// 등록

		Scanner sc = new Scanner(System.in);

		System.out.println("======입력=======");
		System.out.print("고객번호 : ");
		System.out.println(c.getNum());

		System.out.print("고객이름 : ");
		String name = sc.next().trim();

		System.out.print("전화번호 : ");
		String phone = sc.next().trim();

		System.out.print("이메일주소 : ");
		String email = sc.next().trim();

		System.out.print("주소 : ");
		String addr = sc.next().trim();

		if (c.getNum() == 0) {
			// hashMap
			hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
			c.setNum(c.getNum() + 1);
			ff = false;
			result = true;
		} else {
			if (ff != true) {
				hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
				c.setNum(c.getNum() + 1);
				ff = false;
				result = true;
			} else {
				Iterator<Integer> iter = hashMap.keySet().iterator();
				ff = true;
				boolean kf = false;
				while (iter.hasNext()) {
					int key = iter.next();
					CustomerItem customer = hashMap.get(key);
					if (phone.compareTo(customer.getPhone()) == 0 && email.compareTo(customer.getEmail()) == 0) {
						kf = true;
					}
				}
				if (kf != true) {
					hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
					c.setNum(c.getNum() + 1);
					result = true;
				} else {
					System.out.println("전화번호 또는 이메일 중복됨");
					result = false;
				}
			}
		}
		return result;
	}

	public boolean updateCustomer() {
		// 수정
		boolean result = false;
		Scanner sc = new Scanner(System.in);

		System.out.print("고객번호 : ");
		int num = sc.nextInt();

		if (hashMap.containsKey(num)) {
			System.out.println("======변경=======");
			System.out.print("고객이름 : ");
			String name = sc.next().trim();

			System.out.print("전화번호 : ");
			String phone = sc.next().trim();

			System.out.print("주소 : ");
			String addr = sc.next().trim();

			Iterator<Integer> iter = hashMap.keySet().iterator();
			boolean kf = false;
			String email="";
			while (iter.hasNext()) {
				int key = iter.next();
				CustomerItem customer = hashMap.get(key);
				if (phone.compareTo(customer.getPhone()) == 0) {
					kf = true; // 전화번호 또는 이메일주소 중복되면 kf에 true값 저장
				}
				if(num == customer.getNum()) {
					email=customer.getEmail();
				}
			}
			if (kf != true) {
				hashMap.put(num, new CustomerItem(num, name, phone, email, addr));
				result = true;
			} else {
				System.out.println("전화번호 또는 이메일주소 중복됨");
				result = false;
			}
		} else {
			System.out.println("고객번호 없음");
			result = false;
		}
		return result;
	}

	public boolean removeCustomer() {
		// 삭제
		boolean result = false;
		Scanner sc = new Scanner(System.in);

		System.out.print("고객번호 : ");
		int num = sc.nextInt();
		// hashMap
		if (hashMap.containsKey(num)) {
			hashMap.remove(num);
			result = true;
		} else {
			System.out.println("고객번호 없음");
			result = false;
		}
		return result;
	}

	public void showInfo() {
		// TODO Auto-generated method stub
		// 목록
		// hashMap
		Iterator<Integer> iter = hashMap.keySet().iterator();
		System.out.println("고객번호\t고객이름\t전화번호\t\t이메일주소\t\t주소");
		while (iter.hasNext()) {
			int key = iter.next();
			CustomerItem customer = hashMap.get(key);
			System.out.print(customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() + "\t"
					+ customer.getEmail() + "\t" + customer.getAddress() + "\n");
		}
	}

	public boolean searchCustomer() {
		boolean result = false;
		System.out.print("고객번호 : ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		// hashMap
		if (hashMap.containsKey(num)) {
			CustomerItem customer = hashMap.get(num);
			System.out.print("고객정보: " + customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone()
					+ "\t" + customer.getEmail() + "\t" + customer.getAddress() + "\n");
			result = true;
		} else {
			System.out.println("고객번호 없음");
			result = false;
		}
		return result;
	}

}