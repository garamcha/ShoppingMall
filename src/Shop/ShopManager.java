package Shop;

import CustomerInfo.CustomerItem;
import CustomerInfo.CustomerManager;
import Fucntion.PrintInfo;
import ProductInfo.Product;
import ProductInfo.ProductManager;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShopManager extends CustomerManager implements PrintInfo {
	int cnt = 0; // 장바구니 HashMap의 key값을 담당하는 변수
	ProductManager pm; // 제품관리 매니저
	HashMap<Integer, CustomerItem> hashMap = CustomerManager.hashMap; // 고객 정보가 담겨져 있는 hashMap
	CustomerItem currentUser = new CustomerItem(); // 현재 로그인한 회원 정보를 담기 위한 객체
	Scanner scn = new Scanner(System.in);
	HashMap<Integer, Basket> basketHashMap; // 장바구니 정보가 담겨있는 HashMap
	LocalDate now = LocalDate.now(); // 현재 날짜 가져오기
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일"); // 날짜 형식 지정
	String formatedNow = now.format(formatter); // 날짜 포맷팅

	// 생성자
	public ShopManager() throws IOException {
	}

	public ShopManager(ProductManager pManager) throws IOException {
		super();
		this.pm = pManager;
	}


	// 쇼핑몰 메뉴화면 띄우는 함수
	public void shopDisplay() throws IOException {
		readFile(); // 파일 읽어오기
		boolean result = emailCheck();
		if (result == true) {
			while(result) {
				System.out.println("=====환영합니다.=====");
				System.out.println("1. 제품 목록");
				System.out.println("2. 제품 담기");
				System.out.println("3. 장바구니 목록 수정");
				System.out.println("4. 제품 결제하기");
				System.out.println("0. 돌아가기");
				System.out.print("메뉴를 선택하세요. -> ");

				int sel = scn.nextInt();

				switch (sel){
					case 1: // 제품 목록 출력
						showInfo();
						break;
					case 2: // 제품 구매 및 장바구니에 담기
						productBuying();
						break;
					case 3: // 장바구니 목록 수정
						modifyBasket();
						break;
					case 4: // 장바구니 제품 결제하기
						printBasket();
						break;
					case 0: // 메인화면으로 돌아가기
						writerFile();
						result = false;
						break;
					default: // 지정된 숫자 이외의 입력 처리
						System.out.println("잘못된 번호 입력");
						break;
				}


			}
		} else {
			System.out.println("고객관리로 가서 회원가입 해주세요..");
		}
	}

	private void modifyBasket() {
		int cnt = 0; // 제품의 남아있는 재고
			for (int keys : basketHashMap.keySet()) {
				Basket bList = basketHashMap.get(keys);
				if (currentUser.getNum() == bList.getCustomerID()) { // 현재 접속한 고객정보와 장바구니의 고객정보가 동일한 경우에만 출력
					System.out.printf("%11s ", bList.getDate()); // 주문날짜 출력
					for (String item : pm.productList.keySet()) {
						if (pm.productList.get(item).getProduct_code() == bList.getProductID()) {
							System.out.printf("%10s ", pm.productList.get(item).getProduct_name()); // 주문 물품 출력
							// 제품 가격 변경사항 적용하기
							bList.setPrice(pm.productList.get(item).getPrice());
							cnt = pm.productList.get(item).getQuantity(); // 남아 있는 수량
						}
					}
					//										// 개당 금액 출력  	// 주문 수량			 // 총 금액 출력
					System.out.printf("%15s %15s %15s원\n", bList.getPrice(), bList.getQuntityAll(), bList.getPrice() * bList.getQuntityAll());
					System.out.println("1. 제품 수량 변경, 2. 제품 장바구니 삭제 3. 패스 0. 종료");
					int b_sel = scn.nextInt();
					if (b_sel == 1) { // 제품 수량 변경
						System.out.println("현재 제품 수량 : " + cnt);
						System.out.println("변경할 수량 입력: ");
						int modify_cnt = scn.nextInt();
						if (cnt > modify_cnt) { // 변경할 수량을 남아 있는 재고와 비교
							basketHashMap.get(keys).setQuntityAll(modify_cnt);
							System.out.println("수량 변경 성공!!");
						} else {
							System.out.println("재고의 수량을 초과하였습니다.");
						}
					} else if (b_sel == 2) { // 장바구니 삭제
						basketHashMap.remove(keys); // 장바구니 제품 삭제하기
						System.out.println("제품 삭제 성공");
					} else if(b_sel ==3){ // 변경사항 없음
						continue; // 
					} else if (b_sel == 0) { // 장바구니 수정 나가기
						break;
					} else {
						System.out.println("잘못된 번호를 입력하셨습니다.");
					}
				}
			}
		
	}


	// 장바구니에서 구매 함수
	private void basketBuying(int pay, List<Integer> key) {
		if(!(key.isEmpty())){
			System.out.println("총 금액 : " + pay + "원");
			System.out.println("제품을 구매하시겠습니까? y/n");
			String buy_sel = scn.next();

			if(buy_sel.equals("y") || buy_sel.equals("Y")){
				System.out.println(currentUser.getName() + "님 주문 성공!!");
				System.out.println("배송지 : " + currentUser.getAddress());
				System.out.println("연락처 : " + currentUser.getPhone());
				Iterator iter = basketHashMap.keySet().iterator();

				while(iter.hasNext()){
					for(int k : key){
						int b = (int) iter.next();
						if(basketHashMap.get(b).getBasketID() == k){ // 현재 접속 고객의 장바구니 ID 모음만 장바구니 리스트에서 삭제하기
							pm.buyProduct(basketHashMap.get(b).getQuntityAll(), basketHashMap.get(b).getProductID()); // 구매한 제품 수량 뺴기
							iter.remove(); // HashMap 에서 삭제하기
						}
					}
				}
			}else if(buy_sel.equals("n") || buy_sel.equals("N")){
				System.out.println("구매 취소");
			}else{
				System.out.println("잘못된 값 입력");
			}
		}
	}

	// 장바구니 목록 출력하기
	private void printBasket() {
		int pay = 0; // 총 금액
		List<Integer> key = new ArrayList<>(); // 장바구니 키값
		System.out.println("====== ====== ====== 장바구니 ====== ====== ====== ");
		System.out.printf("%s       %10s %12s %13s %15s\n", "주문 날짜", "주문물품", "개당 금액", "주문 수량", "금액");
		
		for(int keys : basketHashMap.keySet()){
			Basket bList = basketHashMap.get(keys);
			if(currentUser.getNum() == bList.getCustomerID()){ // 현재 접속한 고객정보와 장바구니의 고객정보가 동일한 경우에만 출력
				System.out.printf("%11s ", bList.getDate()); // 주문날짜 출력
				key.add(keys); // 현재 접속한 고객의 장바구니 아이디 담기

				for(String item : pm.productList.keySet()){
					if(pm.productList.get(item).getProduct_code() == bList.getProductID()){
						System.out.printf("%10s ", pm.productList.get(item).getProduct_name()); // 주문 물품 출력

						// 제품 가격 변경사항 적용하기
						basketHashMap.get(keys).setPrice(pm.productList.get(item).getPrice());
						bList.setPrice(pm.productList.get(item).getPrice());
					}
				}
				pay += bList.getPrice() * bList.getQuntityAll(); // 주문 수량 * 개당 가격 총 합
														// 개당 금액 출력  	// 주문 수량		// 총 금액 출력
				System.out.printf("%15s %15s %15s원\n",bList.getPrice(), bList.getQuntityAll(),pay);
			}
		}
		
		basketBuying(pay, key); // 장바구니 구매함수
	}

	// 제품 구매 함수
	private void productBuying() {
		System.out.print("구매할 제품 이름 입력: ");
		String pName = scn.next(); // 구매할 제품 이름입력 받기
		Product buyItem = pm.productList.get(pName);
		if(pm.productList.containsKey(pName)){
			buyItem.printValue(); // 제품 정보 출력
			if(buyItem.getQuantity() == 0) {  // 재고가 없는 경우
				System.out.println("재고가 없습니다.");
			} else {
				boolean res = true;
				while(res){
					System.out.print("구매할 수량 입력: ");
					int num = scn.nextInt(); // 구매할 수량 입력 받기
					// 재고와 구매수량 비교하기
					if(num == 0) {// 구매 수량을 0입력했을 때
						System.out.println("1개 이상의 수량을 입력하세요.");
						continue;
					}else if(buyItem.getQuantity() < num){ // 재고보다 구매 수량이 큰경우
						System.out.println("구매수량이 재고를 초과하였습니다.");
					} else{ // 구매가능한 재고인 경우
						int pay = buyItem.getPrice() * num;
						System.out.println("구매 개수: " + num);
						System.out.println("총 금액 : " + pay + "원");
						System.out.println("장바구니에 담으시겠습니까? y/n");
						String choose = scn.next().trim();

						if(choose.equals("y") || choose.equals("Y")){
							// 장바구니 ID, 고객 ID, 제품 ID, 구매날짜, 구매 수량, 총금액
							basketHashMap.put(cnt,
									new Basket(cnt++, currentUser.getNum(), buyItem.getProduct_code(), formatedNow, num, buyItem.getPrice()));
							System.out.println("장바구니에 추가 되었습니다.");
							break;
						}else if (choose.equals("n") || choose.equals("N")){
							System.out.println("장바구니 추가 취소.");
							break;
						}else{
							System.out.println("잘못된 코드 입력");
						}
					}
					break;
				}
			}
		}
		
		
	}

	// 회원가입 된 이메일인 지 확인 하는 함수
	private boolean emailCheck() {
		boolean flag = false;

		Scanner scn = new Scanner(System.in);

		System.out.println("=====과일 쇼핑몰=====");
		System.out.print("이메일 입력: ");
		String email = scn.next().trim();
		Iterator<Integer> iter = hashMap.keySet().iterator();

		while (iter.hasNext()) {
			int key = iter.next();
			CustomerItem customer = hashMap.get(key);
			if (email.compareTo(customer.getEmail()) == 0) { // 입력한 이메일과 회원가입 이메일이 같을떄
				currentUser = customer; // 현재 접속한 유저 정보 저장하기
				System.out.println(customer.getName() + "님이 로그인 했습니다..\n");
				flag = true;
				break;
			}
		}

		if(flag != true) {
			System.out.println("존재하지 않는 이메일 입니다..");
		}
		return flag;
	}
	// 장바구니 정보 csv파일 읽어오기
	@Override
	public void readFile() {
		basketHashMap = new HashMap<>();
		String path = "basket.csv"; // 장바구니 파일 명
		File oderBasket = new File(path);
		String line = "";
		if(oderBasket.exists()){
			try{
				BufferedReader br = new BufferedReader(new FileReader(oderBasket));
				while((line = br.readLine()) != null) { // readLine()으로 한 줄의 데이터 읽어오기
					String[] lineArr = line.split(",");
					int bID = Integer.parseInt(lineArr[0]); // 장바구니 코드
					int cID = Integer.parseInt(lineArr[1]); // 고객정보 코드
					int pID = Integer.parseInt(lineArr[2]); // 제품정보 코드
					String date = lineArr[3]; // 날짜
					cnt = bID+1;
					int quan = Integer.parseInt(lineArr[4]); // 구매 수량
					int price = Integer.parseInt(lineArr[5]); // 제품 가격
					basketHashMap.put(bID, new Basket(bID, cID, pID, date, quan, price));
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	//csv파일에 쓰기
	@Override
	public void writerFile() {
		String path = "basket.csv"; // 장바구니 파일 명
		File oderBasket = new File(path);
		BufferedWriter bw = null; // 출력 스트림 생성
		try{
			bw = new BufferedWriter(new FileWriter(oderBasket, false)); //  덮어쓰기로 저장

			for(int item : basketHashMap.keySet()){
				Basket bItem = basketHashMap.get(item);
				String data = "";
				data = bItem.getBasketID() + "," + bItem.getCustomerID() +"," + bItem.getProductID() +"," + bItem.getDate() +","
						+ bItem.getQuntityAll() +"," + bItem.getPrice() + "\n";
				bw.write(data); // 작성한 데이터를 파일에 넣는다.
			}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(bw != null){
				try {
					bw.flush(); // 남아 있는 데이터까지 보내기
					bw.close(); // 사용한 BufferedWriter를 닫는다.
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}

	}
	// 제품 정보 출력 함수
	@Override
	public void showInfo(){
		for(Product key : pm.p){
			key.printValue();
		}
	}
}
