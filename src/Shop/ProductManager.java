package Shop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

// 제품 정보 클래스
class Product{
    private int product_code;
    private String product_name; // 제품명
    private int price;  // 가격
    private int quantity;   // 재고
    
    // 생성자

    Product(int product_code, String product_name, int price, int quantity){
        this.product_code = product_code;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setProduct_code(int product_code) { this.product_code = product_code; }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_code() { return product_code; }

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
    public void printValue(){
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
    Scanner scn = new Scanner(System.in);
    int num = 0;


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
                    System.out.println("=======================");
                    System.out.println("제품을 등록하세요.");
                    System.out.print("제품명: ");
                    String p_name = scn.next();
                    System.out.print("제품 가격: ");
                    int p_price = scn.nextInt();
                    System.out.print("제품 수량: ");
                    int p_quan = scn.nextInt();
                    p.add(new Product(num++, p_name, p_price, p_quan));
                    break;
                case 2: // 2. 제품 수정
                    System.out.println("=======================");
                    System.out.println("+++++++제품 수정++++++++");
                    System.out.print("수정할 제품 코드 입력: ");
                    int code = scn.nextInt();
                    for(Product a : p){
                        if(a.getProduct_code() == code){
                            System.out.print("제품명: ");
                            String modify_name = scn.next();
                            System.out.print("제품 가격: ");
                            int modify_price = scn.nextInt();
                            System.out.print("제품 수량: ");
                            int modify_quan = scn.nextInt();
                            p.set(code, new Product(code, modify_name, modify_price, modify_quan));
                        }
                    }
                    break;
                case 3: // 3. 제품 삭제
                    System.out.println("=======================");
                    System.out.println("+++++++제품 삭제++++++++");
                    System.out.print("삭제할 제품 코드 입력: ");
                    int del_code = scn.nextInt();

                    Iterator<Product> iter = p.iterator();
                    while(iter.hasNext()){
                        Product product = iter.next();
                        if(product.getProduct_code() == del_code){
                            product.printValue();
                            System.out.print("제품을 삭제하시겠습니까? y/n : ");
                            String check = scn.next().trim();
                            if(check.equals("y") || check.equals("Y")){
                                iter.remove();
                                System.out.println("제품이 삭제되었습니다.");
                            }else if (check.equals("n") || check.equals("N") ){
                                System.out.println("제품 삭제 취소.");
                            }
                            break;
                        }
                    }


                    break;
                case 4: // 4. 제품 목록 출력
                    System.out.println("=======================");
                    System.out.println("+++++++제품 목록++++++++");
                    for (Product a : p) {
                        a.printValue();
                        System.out.println("- - - - - - - - - - - - - - -");
                    }
                    break;
                case 5:
                    System.out.println("=======================");
                    System.out.println("+++++++제품 검색++++++++");
                    System.out.print("검색할 제품 코드 입력: ");
                    int search_code = scn.nextInt();
                    p.get(search_code).printValue();
                    break;
                case 0: // 0. 돌아가기
                    result = false;
                    break;
                default:
                    System.out.println("잘못된 숫자를 입력하셨습니다.");
                    break;

            }
        }

    }
}
