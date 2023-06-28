package CustomerInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import Shop.PrintInfo;

public class CustomerManager implements PrintInfo {
	private static CustomerItem c = new CustomerItem(); // 회원정보 클래스
	protected static HashMap<Integer, CustomerItem> hashMap = new HashMap<>(); // 컬렉션(추가, 수정, 삭제, 출력, 검색 기능 담당)

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
					num = Integer.parseInt(custArr[0].trim());// 배열 0번째 요소(회원번호)
					String name = custArr[1].trim();// 배열 1번째 요소(회원이름)
					String phone = custArr[2].trim();// 배열 2번째 요소(회원전화번호)
					String email = custArr[3].trim();// 배열 3번째 요소(이메일주소)
					String address = custArr[4].trim();// 배열 4번째 요소(사는곳)
					CustomerItem cItem = new CustomerItem(num, name, phone, email, address);
					addCustomerFile(cItem);// 읽어들인 회원정보를 hashMap에 추가하는 메소드 호출

				} catch (NumberFormatException ex) { // 예외처리
					ex.printStackTrace();
				}
				System.out.printf("csv정보: %s\n", strLine); // csv 파일로부터 읽어들인 문자열을 출력함
			}
			c.setNum(num + 1); // 다음에 추가할 회원번호 세팅
		}
	}

	public void addCustomerFile(CustomerItem cItem) {
		hashMap.put(cItem.getNum(), cItem);// addCustomerFile(): 읽어들인 회원정보를 hashMap에 추가하는 메소드
	}

	public void saveToFile() { // saveToFile(): 회원정보를 파일에 내보내는 기능을 하는 메소드
		String path = "customer.csv";// 생성할 csv 파일 이름 지정
		File file = new File(path);
		BufferedWriter bw = null;
		try {	//예외처리

			if (file.createNewFile()) {
				System.out.println("파일 생성됨: " + file.getName());
			} else {
				System.out.println("파일이 이미 존재함");
			}
			bw = new BufferedWriter(new FileWriter(path, true));
			for (int i = 0; i < c.getNum(); i++) {
				String str;
				str = hashMap.get(i).getNum() + "," + hashMap.get(i).getName() + "," + hashMap.get(i).getPhone() + ","
						+ hashMap.get(i).getEmail() + "," + hashMap.get(i).getAddress() + "\n";
				bw.append(str);
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("파일쓰기에 에러 발생");
			e.printStackTrace();
		}
	}

	public void menu(boolean r) {


		while (r) {
			System.out.println();
			System.out.println("=======고객관리======");
			System.out.println("1. 입력");
			System.out.println("2. 수정");
			System.out.println("3. 삭제");
			System.out.println("4. 목록");
			System.out.println("5. 검색");
			System.out.println("0. 돌아가기");

			System.out.print("메뉴 선택: ");
			Scanner sc = new Scanner(System.in);
			int n = sc.nextInt();

			System.out.println();
			boolean result = false;
			switch (n) {
			case 1:
				result = registerCustomer();
				if (result == true) {
					System.out.println("입력 성공!!!");
				} else {
					System.out.println("입력 실패!!!");
				}
				break;
			case 2:
				result = updateCustomer();
				if (result == true) {
					System.out.println("수정 성공!!!");
				} else {
					System.out.println("수정 실패!!!");
				}
				break;
			case 3:
				result = removeCustomer();
				if (result == true) {
					System.out.println("삭제 성공!!!");
				} else {
					System.out.println("삭제 실패!!!");
				}
				break;
			case 4:
				showInfo();
				break;
			case 5:
				searchCustomer();
				break;
			case 0:
				saveToFile();
				r = false;
				break;
			default:
				System.out.println("잘못입력!!!");
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

		System.out.print("이름 : ");
		String name = sc.next().trim();

		System.out.print("전화번호 : ");
		String phone = sc.next().trim();

		System.out.print("이메일 : ");
		String email = sc.next().trim();

		System.out.print("주소 : ");
		String addr = sc.next().trim();

		if (c.getNum() == 0) {
			hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
			c.setNum(c.getNum() + 1);
			result = true;
		} else {
			Iterator<Integer> iter = hashMap.keySet().iterator();

			while (iter.hasNext()) {
				int key = iter.next();
				CustomerItem customer = hashMap.get(key);
				if (phone.compareTo(customer.getPhone()) == 1 && email.compareTo(customer.getEmail()) == 1) {
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

			while (iter.hasNext()) {
				int key = iter.next();
				CustomerItem customer = hashMap.get(key);
				if (phone.compareTo(customer.getPhone()) == 1 && email.compareTo(customer.getEmail()) == 1) {
					hashMap.replace(num, new CustomerItem(num, name, phone, email, addr));
					result = true;
				} else {
					System.out.println("전화번호 또는 이메일중복");
					result = false;
				}
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

		System.out.print("고객번호 입력: ");
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

	public void searchCustomer() {

		System.out.print("찾는 고객번호를 입력: ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		// hashMap
		if (hashMap.containsKey(num)) {
			CustomerItem customer = hashMap.get(num);
			System.out.print("고객정보: " + customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone()
					+ "\t" + customer.getEmail() + "\t" + customer.getAddress() + "\n");
		} else {
			System.out.println("고객번호 없음");
		}
	}

	public void showInfo() {
		// TODO Auto-generated method stub
		// 목록
		// hashMap
		Iterator<Integer> iter = hashMap.keySet().iterator();

		System.out.println("고객번호\t이름\t전화번호\t\t이메일\t\t주소");
		while (iter.hasNext()) {
			int key = iter.next();
			CustomerItem customer = hashMap.get(key);
			System.out.print(customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() + "\t"
					+ customer.getEmail() + "\t" + customer.getAddress() + "\n");
		}
	}


}