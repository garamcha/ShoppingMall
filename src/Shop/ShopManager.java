package Shop;

import java.util.Scanner;

//쇼핑몰 관리 class
public class ShopManager {
    Scanner scn = new Scanner(System.in);
    public void shopDisplay(){
        System.out.println("=====과일 쇼핑몰=====");
        System.out.println("이메일 입력: ");
        String email = scn.next().trim(); // 사용자의 이메일 입력 받기

        // 회원가입 유무 확인하기
        if(true) { // 회원가입이 되어있다면 쇼핑몰로 들어갈 수 있다.
            System.out.println("=====환영합니다.=====");
            System.out.println("1. 제품 구매");
            System.out.println("2. 장바구니");
            System.out.println("0. 돌아가기");
            System.out.print("메뉴 선택 : ");
            int sel = scn.nextInt();
            switch (sel){
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    break;
            }
        }else{ // 회원정보가 없다면 메인화면으로 이동.
            //존재하지 않는 이메일 입니다. 고객관리로 가서 회원가입 해주세오
            // 메인 화면으로 복귀
        }
    }
}
