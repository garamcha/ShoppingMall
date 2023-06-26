import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int sel = 0;
        System.out.println("****************");
        System.out.println("1. 쇼핑몰");
        System.out.println("2. 고객관리");
        System.out.println("3. 제품관리");
        System.out.println("0. 종료");
        System.out.print("메뉴를 선택하세요. -> ");
        sel = scn.nextInt(); // 메뉴입력받기

        switch(sel){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}