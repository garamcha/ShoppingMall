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

import Shop.PrintInfo;

public class CustomerManager implements PrintInfo { // PrintInfo 인터페이스 구현(출력 기능을 수행하는 showInfo() 메소드를 오버라이딩해서 사용함)
	private static CustomerItem c = new CustomerItem(); // 고객정보 클래스
	protected static HashMap<Integer, CustomerItem> hashMap = new HashMap<>(); // 컬렉션(추가, 수정, 삭제, 출력, 검색 기능 담당)
	private static boolean ff = false;

	public CustomerManager() throws IOException { // 생성자 : 파일 읽어옴
		String path = "customer.csv"; // 읽어들일 csv 파일 이름 지정
		File file = new File(path);
		if (file.exists()) { // 파일이 존재할 때
			BufferedReader br = new BufferedReader(new FileReader(file, Charset.forName("UTF-8"))); // 파일을 읽어옴. 문제점
																									// 개선(Charset :
																									// 한글깨짐현상 방지)
			String strLine = null;
			String[] custArr = null;
			int num = 0;
			while ((strLine = br.readLine()) != null) { // 반복문: null이 아닐 때까지 한줄씩 읽어들임
				custArr = strLine.split(","); // 읽어들인 코드를 ','단위로 분리함
				try { // 예외처리
					num = Integer.parseInt(custArr[0].trim());// 배열 0번째 요소(고객번호)
					String name = custArr[1].trim();// 배열 1번째 요소(고객이름)
					String phone = custArr[2].trim();// 배열 2번째 요소(고객전화번호)
					String email = custArr[3].trim();// 배열 3번째 요소(이메일주소)
					String address = custArr[4].trim();// 배열 4번째 요소(사는곳)
					CustomerItem cItem = new CustomerItem(num, name, phone, email, address);
					addCustomerFile(cItem);// 읽어들인 고객정보를 hashMap에 추가하는 메소드 호출
					ff = true;
				} catch (NumberFormatException ex) { // 예외처리
					ex.printStackTrace();
				}
				System.out.printf("csv정보: %s\n", strLine); // csv 파일로부터 읽어들인 문자열을 출력함
			}
			c.setNum(num + 1); // 다음에 추가할 고객번호 세팅
		}
	}

	public void addCustomerFile(CustomerItem cItem) {
		hashMap.put(cItem.getNum(), cItem);// void addCustomerFile(): 읽어들인 고객정보를 hashMap에 추가하는 메소드
	}

	public void saveToFile() { // void saveToFile(): 고객정보를 파일에 내보내는 기능을 하는 메소드
		String path = "customer.csv";// 생성할 csv 파일 이름 지정
		File file = new File(path);
		BufferedWriter bw = null;
		try { // 예외처리
			if (file.createNewFile()) {// 새파일을 생성했을 때
				System.out.println("파일 생성됨: " + file.getName()); // 출력코드(파일 생성됨: 파일이름)
			} else { // 파일이 이미 존재할 때
				System.out.println("파일이 이미 존재함"); // 출력코드(파일이 이미 존재함)
			}
			bw = new BufferedWriter(new FileWriter(path, true)); // 파일 쓰기 객체 생성(path명: customer.csv)
			for (int i = 0; i < c.getNum(); i++) { // 반복문
				String str; // str: 파일에 한줄 단위로 쓰기 위한 문자열을 저장(',' 구분자 사용)
				str = hashMap.get(i).getNum() + "," + hashMap.get(i).getName() + "," + hashMap.get(i).getPhone() + ","
						+ hashMap.get(i).getEmail() + "," + hashMap.get(i).getAddress() + "\n";
				bw.append(str); // 파일에 쓰기 위해 문자열을 append함
			}
			bw.close(); // 파일 쓰기 객체 종료
		} catch (IOException e) {
			System.out.println("파일쓰기에 에러 발생"); // 출력 코드
			e.printStackTrace();
		}
	}

	/*
	 * void menu()메소드 : 고객관리 메뉴 보여주는 기능 수행하는 코드 구현(입력, 수정, 삭제, 목록보기, 검색하기, 이전메뉴로
	 * 돌아가기 등) 메뉴 선택(int타입의 변수 n에 해당메뉴번호를 입력받아서 while 반복문 안에 switch-case문으로 돌림)
	 * 유효성검사(boolean타입의 변수 result에 결과값(true/false)을 반환받아서 출력코드(OO성공/OO실패) 코드 작성함)
	 * 
	 * boolean registerCustomer() 메소드 : 고객정보 입력받는 코드 구현 boolean updateCustomer() 메소드
	 * : 고객정보 수정하는 코드 구현 boolean removeCustomer() 메소드 : 고객정보 삭제하는 코드 구현 void
	 * showInfo() 메소드 : 전체 고객정보 출력하는 코드 구현 void searchCustomer() 메소드 : 검색한 고객정보를
	 * 출력하는 코드 구현 void saveToFile() 메소드: : 고객정보를 파일에 쓰기로 내보내는 코드 구현
	 */
	public void menu(boolean r) { // void menu()메소드 : 고객관리 메뉴 보여주는 기능 수행하는 코드 구현(입력, 수정, 삭제, 목록보기, 검색하기, 이전메뉴로
									// 돌아가기 등)
									// boolean r : true 값을 받아와서 고객관리 menu() 메소드에서 while 반복문을 수행할 때 사용함
									// (돌아가기(0번)를 선택하면 false값을 출력하고 고객관리 menu()메소드를 종료함)

		while (r) {
			System.out.println();
			System.out.println("=======고객관리======");
			System.out.println("1. 입력");
			System.out.println("2. 수정");
			System.out.println("3. 삭제");
			System.out.println("4. 목록");
			System.out.println("5. 검색");
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
			boolean result = false; // boolean result : 유효성검사를 수행하기 위해 사용하는 변수
			switch (n) {
			case 1:
				result = registerCustomer(); // boolean registerCustomer() 메소드 : 고객정보 입력(성공하면 true값 반환해서 출력함)
				if (result == true) {
					System.out.println("입력 성공!!!");
				} else {
					System.out.println("입력 실패!!!");
				}
				break;
			case 2:
				result = updateCustomer(); // boolean updateCustomer() 메소드 : 고객정보 수정(성공하면 true값 반환해서 출력함)
				if (result == true) {
					System.out.println("수정 성공!!!");
				} else {
					System.out.println("수정 실패!!!");
				}
				break;
			case 3:
				result = removeCustomer(); // boolean removeCustomer() 메소드 : 고객정보 삭제(성공하면 true값 반환해서 출력함)
				if (result == true) {
					System.out.println("삭제 성공!!!");
				} else {
					System.out.println("삭제 실패!!!");
				}
				break;
			case 4:
				showInfo(); // void showInfo() 메소드 : 전체 고객정보 출력(메소드 오버라이딩)
				break;
			case 5:
				searchCustomer(); // void searchCustomer() 메소드 : 검색한 고객정보를 출력
				break;
			case 0:
				saveToFile(); // void saveToFile() 메소드: : 고객정보를 파일에 쓰기로 내보냄
				r = false; // 변수 r에 while반복문을 종료하는 값 저장함
				break;
			default:
				System.out.println("잘못입력!!!"); // 출력 코드(switch-case문에 해당 값이 없을 때 "잘못입력!!!" 출력)
				break;
			}
		}
	}

	public boolean registerCustomer() { // boolean registerCustomer() 메소드 : 고객정보 입력받는 코드 구현
		boolean result = false;
		// 등록

		Scanner sc = new Scanner(System.in);

		System.out.println("======입력=======");
		System.out.print("고객번호 : "); // 고객번호는 고유한 값
		System.out.println(c.getNum()); // 사용자에게 입력받지 않고 getter로 불러서 사용함

		System.out.print("이름 : "); // 고객이름은 중복가능
		String name = sc.next().trim(); // 입력받는다

		System.out.print("전화번호 : "); // 고객전화번호는 중복불가능
		String phone = sc.next().trim();// 입력받는다

		System.out.print("이메일 : "); // 고객이메일주소는 중복불가능(쇼핑몰 메뉴에서 이메일입력받을 때 사용함)
		String email = sc.next().trim();// 입력받는다

		System.out.print("주소 : "); // 주소는 중복가능
		String addr = sc.next().trim(); // 입력받는다

		if (c.getNum() == 0) {// 생성된 customer.csv 파일이 없을 때 처음 한 번은 그냥 추가함(고객번호가 초기값일 때)
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
					if (phone.compareTo(customer.getPhone()) ==0 && email.compareTo(customer.getEmail()) ==0) {
						kf = true;
					}
				}
				if (kf != true) {
					hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
					c.setNum(c.getNum() + 1);
					result = true;
				} else {
					System.out.println("전화번호 또는 이메일중복");
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

		System.out.print("고객번호 입력: ");
		int num = sc.nextInt();

		if (hashMap.containsKey(num)) {
			System.out.println("======수정=======");
			System.out.print("이름 : ");
			String name = sc.next().trim();

			System.out.print("전화번호 : ");
			String phone = sc.next().trim();

			System.out.print("이메일 : ");
			String email = sc.next().trim();

			System.out.print("주소 : ");
			String addr = sc.next().trim();

			Iterator<Integer> iter = hashMap.keySet().iterator();
			boolean kf = false;
			while (iter.hasNext()) {
				int key = iter.next();
				CustomerItem customer = hashMap.get(key);
				if (phone.compareTo(customer.getPhone()) ==0 && email.compareTo(customer.getEmail()) ==0) {
					kf = true;
				}
			}
			if (kf != true) {
				hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
				c.setNum(c.getNum() + 1);
				result = true;
			} else {
				System.out.println("전화번호 또는 이메일중복");
				result = false;
			}
		} else {
			System.out.println("고객번호 없음");
			result = false;
		}
		return result;
	}

	public boolean removeCustomer() { // boolean removeCustomer() 메소드 : 고객정보 삭제하는 코드 구현
		// 삭제
		boolean result = false;
		Scanner sc = new Scanner(System.in);

		System.out.print("고객번호 입력: ");
		int num = sc.nextInt();
		// hashMap
		if (hashMap.containsKey(num)) { // 입력받은 고객번호가 있을 때
			hashMap.remove(num); // 해당 고객번호와 일치하는 hashMap 데이터를 삭제
			result = true; // 삭제 성공시 변수 result 값에 true 저장
		} else { // 입력받은 고객번호가 없을 때
			System.out.println("고객번호 없음"); // 출력 코드("고객번호 없음")
			result = false;
		}
		return result; // result값 반환
	}

	public void searchCustomer() { // void searchCustomer() 메소드 : 검색한 고객정보를 출력하는 코드 구현
		System.out.print("찾는 고객번호를 입력: ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		// hashMap
		if (hashMap.containsKey(num)) { // 입력받은 고객번호가 있을 때
			CustomerItem customer = hashMap.get(num); // 변수 num에 해당하는 hashMap 데이터를 CusotmerItem타입의 customer변수에 값을 저장함
			System.out.print("고객정보: " + customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() // 출력
																													// 코드("고객정보:
																													// OO
																													// OO
																													// OO
																													// OO
																													// OO")
					+ "\t" + customer.getEmail() + "\t" + customer.getAddress() + "\n");
		} else { // 입력받은 고객번호가 없을 때
			System.out.println("고객번호 없음"); // 출력 코드("고객번호 없음")
		}
	}

	public void showInfo() { // void showInfo() 메소드 : 전체 고객정보 출력하는 코드 구현
		// TODO Auto-generated method stub
		// 목록
		// hashMap
		Iterator<Integer> iter = hashMap.keySet().iterator(); // Interface Iterator<E> : Iterators allow the caller to
																// remove elements from the underlying collection during
																// the iteration with well-defined semantics.
																// Set<K> keySet() : Returns a Set view of the keys
																// contained in this map.

		System.out.println("고객번호\t이름\t전화번호\t\t이메일\t\t주소"); // 출력할 고객정보의 헤드정보 출력
		while (iter.hasNext()) { // 변수 key에 다음 요소를 저장함
			int key = iter.next(); // 변수 key에 해당하는 hashMap 데이터를 CusotmerItem타입의 customer변수에 값을 저장함
			CustomerItem customer = hashMap.get(key); // 변수 key에 해당하는 hashMap 데이터를 CusotmerItem타입의 customer변수에 값을 저장함
			System.out.print(customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() + "\t" // 출력 코드
					+ customer.getEmail() + "\t" + customer.getAddress() + "\n");
		}
	}

}