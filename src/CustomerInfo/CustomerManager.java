package CustomerInfo;

import CustomerInfo.CustomerItem;

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

public class CustomerManager {
	static CustomerItem c = new CustomerItem();
	static HashMap<Integer, CustomerItem> hashMap = new HashMap<>();


	public CustomerManager() throws IOException {
		String path = "customer.csv";
		File file = new File(path);
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file, Charset.forName("UTF-8"))); // 파일을 읽기
			String strLine = null;
			String[] custArr = null;
			int num = 0;
			while ((strLine = br.readLine()) != null) {
				custArr = strLine.split(",");
				try {
					num = Integer.parseInt(custArr[0].trim());
					String name = custArr[1].trim();
					String phone = custArr[2].trim();
					String email = custArr[3].trim();
					String address = custArr[4].trim();
					CustomerItem cItem = new CustomerItem(num, name, phone, email, address);
					addCustomerFile(cItem);

				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
				System.out.printf("csv정보: %s\n", strLine); // 읽어들인 문자열을 출력함
			}
			c.setNum(num+1);

		}
	}

	public void addCustomerFile(CustomerItem cItem) {
		hashMap.put(cItem.getNum(), cItem);
	}

	public static void saveToFile() { // 파일에 쓰기
		String path = "customer.csv";
		File file = new File(path);
		BufferedWriter bw = null;
		try {
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

	public static void menu(boolean r) {

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
					result = regCustomer();
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
					listCustomer();
					break;
				case 5:
					searchCustomer();
					break;
				case 0:
					r = false;
					saveToFile();
					break;
				default:
					System.out.println("잘못입력!!!");
					break;
			}
		}
	}

	public static boolean regCustomer() {
		// 등록
		boolean result = false;
		Scanner sc = new Scanner(System.in);

		System.out.println("======입력=======");
		System.out.print("고객번호 : ");

		System.out.println(c.getNum());

		System.out.print("이름 : ");
		String name = sc.next();

		System.out.print("전화번호 : ");
		String phone = sc.next();

		System.out.print("이메일 : ");
		String email = sc.next();

		System.out.print("주소 : ");
		String addr = sc.next();

		if (!hashMap.containsValue(phone) && !hashMap.containsValue(email)) {
			// hashMap
			hashMap.put(c.getNum(), new CustomerItem(c.getNum(), name, phone, email, addr));
			c.setNum(c.getNum() + 1);
			result = true;

		} else {
			System.out.println("전화번호 또는 이메일중복");
		}
		return result;

	}

	public static boolean updateCustomer() {
		// 수정
		boolean result = false;
		Scanner sc = new Scanner(System.in);

		System.out.print("고객번호 입력: ");
		int num = sc.nextInt();

		if (hashMap.containsKey(num)) {
			System.out.println("======수정=======");
			System.out.print("이름 : ");
			String name = sc.next();

			System.out.print("전화번호 : ");
			String phone = sc.next();

			System.out.print("이메일 : ");
			String email = sc.next();

			System.out.print("주소 : ");
			String addr = sc.next();

			// hashMap
			hashMap.replace(num, new CustomerItem(num, name, phone, email, addr));
			result = true;
		} else {
			System.out.println("고객번호 없음");
			result = false;
		}
		return result;
	}

	public static boolean removeCustomer() {
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

	public static void listCustomer() {
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

	public static void searchCustomer() {
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

}