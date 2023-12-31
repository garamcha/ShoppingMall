
import java.io.IOException;
import java.util.Scanner;

import CustomerInfo.CustomerManager;
import ProductInfo.ProductManager;
import Shop.ShopManager;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        ProductManager p_manager = new ProductManager();
        CustomerManager c_manager = new CustomerManager();
        ShopManager s_manager = new ShopManager(p_manager);
        boolean res = true;
        int sel = 0;
        while (res) {
            System.out.println("****************");
            System.out.println("1. 쇼핑몰");
            System.out.println("2. 고객관리");
            System.out.println("3. 제품관리");
            System.out.println("0. 종료");
            System.out.print("메뉴를 선택하세요. -> ");
            sel = scn.nextInt(); // 메뉴입력받기
            switch (sel) {
                case 1:
                    s_manager.shopDisplay(); // 쇼핑몰 화면 출력
                    break;
                case 2:
                    c_manager.menu(true); // 고객관리 화면 출력
                    break;
                case 3:
                    p_manager.login(true); // 제품 관리 화면 출력
                    break;
                case 0:
                    System.out.println("프로그램 종료..");
                    res = false;
                    break;
                default:
                    break;
            }
        }
    }
}
