package Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CustomerMain {
	static Customer c = new Customer();
	static HashMap<Integer, Customer> hashMap = new HashMap<>();
	static int cNum = 0;

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
			hashMap.put(cNum, new Customer(cNum, name, phone, email, addr));
			cNum++;
			c.setNum(cNum);
			result = true;
		} else {
			System.out.println("전화번호 또는 이메일 중복");
			result = false;
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

			c.setName(name);
			c.setPhone(phone);
			c.setEmail(email);
			c.setAddress(addr);
			// hashMap
			hashMap.replace(num, new Customer(num, c.getName(), c.getPhone(), c.getEmail(), c.getAddress()));
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
			Customer customer = hashMap.get(key);
			System.out.print(customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() + "\t"
					+ customer.getEmail() + "\t" + customer.getAddress() + "\n");
		}
	}

	public static void searchCustomer() {
		System.out.print("찾는 고객번호를 입력: ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		// hashMap
		if(hashMap.containsKey(num)) {
		Customer customer = hashMap.get(num);
		System.out.print("고객정보: " + customer.getNum() + "\t" + customer.getName() + "\t" + customer.getPhone() + "\t"
				+ customer.getEmail() + "\t" + customer.getAddress() + "\n");
		}else {
			System.out.println("고객번호 없음");
		}
	}

}
