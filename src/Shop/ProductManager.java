package Shop;

import java.util.ArrayList;
import java.util.Scanner;

// 제품 정보 클래스
class Product{
    private String product_name; // 제품명
    private int price;  // 가격
    private int quantity;   // 수량
    
    // 생성자

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}

public class ProductManager {
    public class Select(String[] args) {
        ArrayList<Product> p = new ArrayList<Product>(); // 제품정보를 담고 있는 리스트
        Scanner scn = new Scanner(System.in);
        int p_sel = 0;
        System.out.println("***** 제품 관리 메뉴 *****");
        System.out.println("1. 제품 등록");
        System.out.println("2. 제품 수정");
        System.out.println("3. 제품 삭제");
        System.out.println("4. 제품 목록 출력");
        System.out.println("0. 돌아가기");
        System.out.print("메뉴를 선택하세요. -> ");
        p_sel = scn.nextInt();

        switch (p_sel){
            case 1: // 1. 제품 등록
                break;
            case 2: // 2. 제품 수정
                break;
            case 3: // 3. 제품 삭제
                break;
            case 4: // 4. 제품 목록 출력
                break;
            case 0: // 0. 돌아가기
                break;
            default:
                System.out.println("잘못된 숫자를 입력하셨습니다.");
                break;

        }

    }

}
