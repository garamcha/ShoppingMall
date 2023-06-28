package ProductInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

// 제품 정보 클래스
class Product {
	private int product_code;
	private String product_name; // 제품명
	private int price; // 가격
	private int quantity; // 재고

	// 생성자

	Product(int product_code, String product_name, int price, int quantity) {
		this.product_code = product_code;
		this.product_name = product_name;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProduct_code() {
		return product_code;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getProduct_name() {
		return product_name;
	}

	// 테스트 용
	public void printValue() {
		System.out.println("=======제품 정보=======");
		System.out.print("제품 코드: ");
		System.out.println(this.product_code);
		System.out.print("제품명: ");
		System.out.println(this.product_name);
		System.out.print("제품 가격: ");
		System.out.println(this.price);
		System.out.print("제품 수량: ");
		System.out.println(this.quantity);
	}
}

public class ProductManager {
	ArrayList<Product> p = new ArrayList<Product>(); // 제품정보를 담고 있는 리스트
	HashMap<String, Product> productList = new HashMap<>();
	Scanner scn = new Scanner(System.in);
	int num = 0;

	// 생성자
	public ProductManager() throws IOException {
	}

	{
		String path = "product.csv"; // 엑셀 파일 명
		File file = new File(path);
		if (file.exists()) {
			BufferedReader inFile = new BufferedReader(new FileReader(file));
			String sLine = null;
			while ((sLine = inFile.readLine()) != null) {
				String[] arr = sLine.split(",");
				try {
					int code = Integer.parseInt(arr[0].trim());
					String p_name = arr[1].trim();
					int price = Integer.parseInt(arr[2].trim());
					int quantity = Integer.parseInt(arr[3].trim());
					num = code + 1;
					p.add(new Product(code, p_name, price, quantity));
					productList.put(p_name, new Product(code, p_name, price, quantity));
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
				System.out.println("읽어들인 product 문자열 출력: " + sLine);
			}
		}
	}

	// 제품 관리 선택 메뉴
	public void display(boolean result) { //
		int p_sel = 0;

		while (result) {
			System.out.println("***** 제품 관리 메뉴 *****");
			System.out.println("1. 제품 등록");
			System.out.println("2. 제품 수정");
			System.out.println("3. 제품 삭제");
			System.out.println("4. 제품 목록 출력");
			System.out.println("5. 제품 검색");
			System.out.println("0. 돌아가기");
			System.out.print("메뉴를 선택하세요. -> ");

			p_sel = scn.nextInt();
			switch (p_sel) {
			case 1: // 1. 제품 등록
				boolean add_result = true;
				while (add_result) {
					add_result = addProduct();
				}
				break;
			case 2: // 2. 제품 수정
				modifyProduct();
				break;
			case 3: // 3. 제품 삭제
				deleteProduct();
				break;
			case 4: // 4. 제품 목록 출력
				System.out.println("=======================");
				System.out.println("+++++++제품 목록++++++++");
				/*
				 * // HashMap 사용 for(String key : productList.keySet()){
				 * productList.get(key).printValue(); }
				 */
				// ArrayList 사용
				for (Product a : p) {
					a.printValue();
					System.out.println("- - - - - - - - - - - - - - -");
				}
				break;
			case 5: // 5. 제품 검색
				searchProduct();
				break;
			case 0: // 0. 돌아가기
				saveToFile();
				result = false;
				break;
			default:
				System.out.println("잘못된 숫자를 입력하셨습니다.");
				break;

			}
		}

	}

	private void searchProduct() {
		System.out.println("=======================");
		System.out.println("+++++++제품 검색++++++++");
		System.out.print("검색할 제품명 입력: ");
		// HashMap 사용
		String search_name = scn.next().trim();
		for (String key : productList.keySet()) {
			if (productList.containsKey(search_name)) {
				productList.get(key).printValue();
			} else {
				System.out.println("존재하지 않는 제품입니다.");
			}
		}

		/*
		 * // ArrayList 사용 System.out.print("검색할 제품 코드 입력: "); int search_code =
		 * scn.nextInt(); p.get(search_code).printValue();
		 */
	}

	// 제품 등록 함수
	private boolean addProduct() {
		String p_name;
		System.out.println("=======================");
		System.out.println("등록 나가기. -> -1 입력");
		System.out.println("제품을 등록하세요.");
		while (true) {
			System.out.print("제품명: ");
			p_name = scn.next();
			if (productList.containsKey(p_name)) {
				System.out.println("이미 존재하는 제품명입니다.");
			} else
				break;
		}
		if (p_name.equals("-1")) {
			System.out.println("제품 등록 종료");
			return false;
		}
		System.out.print("제품 가격: ");
		int p_price = scn.nextInt();
		if (p_price == -1) {
			System.out.println("제품 등록 종료");
			return false;
		}
		System.out.print("제품 수량: ");
		int p_quan = scn.nextInt();
		if (p_quan == -1) {
			System.out.println("제품 등록 종료");
			return false;
		} else {
			p.add(new Product(num, p_name, p_price, p_quan)); // ArrayList 추가
			productList.put(p_name, new Product(num++, p_name, p_price, p_quan));
			System.out.println("제품 등록 성공!!");
			return true;
		}
	}

	// 제품 수정 함수
	private void modifyProduct() {
		System.out.println("=======================");
		System.out.println("+++++++제품 수정++++++++");
		System.out.print("수정할 제품명 입력: ");
		String pro_name = scn.next();

		for (String key : productList.keySet()) {
			if (key.equals(pro_name)) {
				Product check = productList.get(pro_name);
				check.printValue();
				System.out.println("---------------------");
				System.out.print("제품명: ");
				String modify_name = scn.next();
				System.out.print("제품 가격: ");
				int modify_price = scn.nextInt();
				System.out.print("제품 수량: ");
				int modify_quan = scn.nextInt();
				productList.remove(key);
				productList.put(modify_name,
						new Product(check.getProduct_code(), modify_name, modify_price, modify_quan));
				p.set(check.getProduct_code(),
						new Product(check.getProduct_code(), modify_name, modify_price, modify_quan));
				// productList.replace(pro_name, new Product( check.getProduct_code(),
				// modify_name, modify_price, modify_quan));
				break;
			}
		}

		/*
		 * // ArrayList 사용 System.out.print("수정할 제품 코드 입력: "); int code = scn.nextInt();
		 * for(Product a : p){ if(a.getProduct_code() == code){ a.printValue();
		 * System.out.println("---------------------"); System.out.print("제품명: ");
		 * String modify_name = scn.next(); System.out.print("제품 가격: "); int
		 * modify_price = scn.nextInt(); System.out.print("제품 수량: "); int modify_quan =
		 * scn.nextInt(); p.set(code, new Product(code, modify_name, modify_price,
		 * modify_quan)); System.out.print("제품 수량: "); } }
		 */
	}

	// 제품 삭제 함수
	private void deleteProduct() {
		System.out.println("=======================");
		System.out.println("+++++++제품 삭제++++++++");
		System.out.print("삭제할 제품명 입력: ");
		String del_name = scn.next();
		for (String key : productList.keySet()) {
			if (key.equals(del_name)) {
				Product p_info = productList.get(del_name);
				p_info.printValue();
				System.out.println("---------------------");
				System.out.print("제품을 삭제하시겠습니까? y/n : ");
				String check = scn.next().trim();
				if (check.equals("y") || check.equals("Y")) {
					Iterator<Product> iter = p.iterator();
					while (iter.hasNext()) {
						Product product = iter.next();
						if (product.getProduct_code() == productList.get(key).getProduct_code()) {
							productList.remove(key);
							iter.remove();
							break;
						}
					}
					System.out.println("제품이 삭제되었습니다.");
				} else if (check.equals("n") || check.equals("N")) {
					System.out.println("제품 삭제 취소.");
				} else {
					System.out.println("잘못된 입력.");
				}
				break;
			}
		}
		/*
		 * System.out.print("삭제할 제품 코드 입력: "); int del_code = scn.nextInt();
		 * 
		 * Iterator<Product> iter = p.iterator(); while(iter.hasNext()){ Product product
		 * = iter.next(); if(product.getProduct_code() == del_code){
		 * product.printValue(); System.out.print("제품을 삭제하시겠습니까? y/n : "); String check
		 * = scn.next().trim(); if(check.equals("y") || check.equals("Y")){
		 * iter.remove(); System.out.println("제품이 삭제되었습니다."); }else if
		 * (check.equals("n") || check.equals("N") ){ System.out.println("제품 삭제 취소.");
		 * }else{ System.out.println("잘못된 입력."); } break; }else{
		 * System.out.println("제품이 존재하지 않습니다."); } }
		 */

	}

	private void saveToFile() {
		String path = "product.csv";
		File file = new File(path); // 현재 파일이 저장되어 있는 위치에 파일 생성
		BufferedWriter writer = null;

		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}

			writer = new BufferedWriter(new FileWriter(path, false)); // append의 값이 true면 이어붙이기 false면 덮어쓰기
			for (Product item : p) {
				String str;
				str = item.getProduct_code() + "," + item.getProduct_name() + "," + item.getPrice() + ","
						+ item.getQuantity() + "\n";
				writer.append(str);
			}
			writer.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
