package Shop;

import CustomerInfo.CustomerItem;
import CustomerInfo.CustomerManager;

public class ShopManager extends CustomerManager {

	public ShopManager() throws IOException {
		super();
	}

	public void shopDisplay() {
		boolean result = emailCheck();
		if (result == true) {
			while(true) {
				System.out.println("=====환영합니다.=====");
				System.out.println("1. 제품구매");
				System.out.println("2. 장바구니");
				System.out.print("메뉴를 선택하세요. -> ");
				
				Scanner scn = new Scanner(System.in);
				int selected = scn.nextInt();
				
				System.out.println("로그인중..");
			}
		} else {
			System.out.println("고객관리로 가서 회원가입 해주세요..");
		}
	}

	public boolean emailCheck() {
		boolean flag = false;
		HashMap<Integer, CustomerItem> hashMap = CustomerManager.hashMap;
		Scanner scn = new Scanner(System.in);

		System.out.println("=====과일 쇼핑몰=====");
		System.out.print("이메일 입력: ");
		String email = scn.next().trim();
		Iterator<Integer> iter = hashMap.keySet().iterator();

		while (iter.hasNext()) {
			int key = iter.next();
			CustomerItem customer = hashMap.get(key);
			if (customer.getEmail().equals(email)) {
				System.out.println(customer.getName() + "님이 로그인 했습니다..\n");
				flag = true;
				break;
			} else {
				System.out.println("존재하지 않는 이메일 입니다..");
				flag = false;
				break;
			}
		}
		return flag;
	}
}
