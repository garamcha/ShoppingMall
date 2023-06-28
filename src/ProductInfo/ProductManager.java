package ProductInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

// 제품 정보 클래스
class Product{
    private int product_code; // 제품 코드
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

    // getter
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

    // 제품 정보 전체 출력 함수
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

// 제품관리 클래스
public class ProductManager {
    ArrayList<Product> p = new ArrayList<Product>(); // 제품정보를 담고 있는 리스트
    HashMap<String, Product> productList = new HashMap<>(); // 제품정보를 담고 있는 HashMap
    Scanner scn = new Scanner(System.in);
    int num = 0; // 제품코드

    // 생성자
    public ProductManager() throws IOException{}{
        /* csv 파일에 저장되어 있는 데이터 가져와서 HashMap, ArrayList에 저장 */
        String path = "product.csv"; // 엑셀 파일 명
        File file = new File(path); // 파일 생성
        if(file.exists()){
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            String sLine = null;
            while((sLine = inFile.readLine()) != null){ //한줄씩 데이터 읽어오기
                String[] arr = sLine.split(","); // ',' 로 데이터 분리
                try {
                    int code = Integer.parseInt(arr[0].trim());
                    String p_name = arr[1].trim();
                    int price = Integer.parseInt(arr[2].trim());
                    int quantity = Integer.parseInt(arr[3].trim());
                    num = code+1;
                    p.add(new Product(code, p_name, price, quantity)); // 리스트에 저장
                    productList.put(p_name, new Product(code, p_name, price, quantity)); // hashmap에 저장
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                //System.out.println("읽어 드린 문자열 출력: " + sLine);
            }
        }
    }

    // 제품 관리 선택 메뉴
    public void display(boolean result) { //
        int p_sel = 0;

        while (result) {
            // -------------- 메뉴 목록 출력
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
                    while(add_result){ //사용자가 입력을 종료하기 전까지 반복
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
                    /*// HashMap 사용
                    for(String key : productList.keySet()){
                        productList.get(key).printValue();
                    }*/
                     // ArrayList 사용
                    for (Product a : p) {
                        a.printValue(); // 제품 정보 출력
                        System.out.println("- - - - - - - - - - - - - - -");
                    }
                    break;
                case 5: // 5. 제품 검색
                    searchProduct();
                    break;
                case 0: // 0. 돌아가기
                    saveToFile(); // csv에 저장하기
                    result = false;
                    break;
                default: // 지정된 숫자 입력 외의 처리
                    System.out.println("잘못된 숫자를 입력하셨습니다.");
                    break;

            }
        }

    }

    // 제품 검색 함수
    private void searchProduct() {
        System.out.println("=======================");
        System.out.println("+++++++제품 검색++++++++");
        System.out.print("검색할 제품명 입력: ");
        // HashMap 사용
        String search_name = scn.next().trim(); // 검색할 제품명 입력
        for(String key: productList.keySet()){
            if(productList.containsKey(search_name)){ // 해당 문자열이 포함되어있는지
                productList.get(key).printValue();
            }else {
                System.out.println("존재하지 않는 제품입니다.");
            }
        }

        /*// ArrayList 사용
        System.out.print("검색할 제품 코드 입력: ");
        int search_code = scn.nextInt();
        p.get(search_code).printValue();*/
    }

    // 제품 등록 함수
    private boolean addProduct(){
        String p_name;
        System.out.println("=======================");
        System.out.println("등록 나가기. -> -1 입력");
        System.out.println("제품을 등록하세요.");
        while(true){ // 제품명이 중복일 때 반복
            System.out.print("제품명: ");
            p_name = scn.next(); // 제품명 입력
            if(productList.containsKey(p_name)) {
                System.out.println("이미 존재하는 제품명입니다.");
            } else break;
        }
        if(p_name.equals("-1")) { // 제품 등록 종료 코드
            System.out.println("제품 등록 종료");
            return false;
        }
        System.out.print("제품 가격: ");
        int p_price = scn.nextInt(); // 제품 가격 입력
        if(p_price == -1) {// 제품 등록 종료 코드
            System.out.println("제품 등록 종료");
            return false;
        }
        System.out.print("제품 수량: ");
        int p_quan = scn.nextInt(); // 제품 수량 입력
        if(p_quan == -1) {// 제품 등록 종료 코드
            System.out.println("제품 등록 종료");
            return false;
        }
        else {
            p.add(new Product(num, p_name, p_price, p_quan)); // ArrayList 추가
            productList.put(p_name, new Product(num++, p_name, p_price, p_quan)); // HashMap 추가
            System.out.println("제품 등록 성공!!");
            return true;
        }
    }
    // 제품 수정 함수
    private void modifyProduct(){
        System.out.println("=======================");
        System.out.println("+++++++제품 수정++++++++");
        System.out.print("수정할 제품명 입력: ");
        String pro_name = scn.next(); // 수정할 제품명 입력

        for(String key : productList.keySet()){
            if(key.equals(pro_name)){
                Product check = productList.get(pro_name);
                check.printValue();
                System.out.println("---------------------");
                System.out.print("제품명: ");
                String modify_name = scn.next(); // 제품명 수정
                System.out.print("제품 가격: ");
                int modify_price = scn.nextInt(); // 가격 수정
                System.out.print("제품 수량: ");
                int modify_quan = scn.nextInt();    // 수량 수정
                productList.remove(key); // 키값인 제품명이 변경되기 때문에 삭제 후 다시 저장한다.
                productList.put(modify_name, new Product( check.getProduct_code(), modify_name, modify_price, modify_quan)); // HashMap에 저장
                p.set(check.getProduct_code(), new Product(check.getProduct_code(), modify_name, modify_price, modify_quan)); // ArrayList에 저장
                //productList.replace(pro_name, new Product( check.getProduct_code(), modify_name, modify_price, modify_quan));
                break;
            }
        }

         /* // ArrayList 사용
        System.out.print("수정할 제품 코드 입력: ");
        int code = scn.nextInt();
        for(Product a : p){
            if(a.getProduct_code() == code){
                a.printValue();
                System.out.println("---------------------");
                System.out.print("제품명: ");
                String modify_name = scn.next();
                System.out.print("제품 가격: ");
                int modify_price = scn.nextInt();
                System.out.print("제품 수량: ");
                int modify_quan = scn.nextInt();
                p.set(code, new Product(code, modify_name, modify_price, modify_quan));
                System.out.print("제품 수량: ");
            }
        }*/
    }

    // 제품 삭제 함수
    private void deleteProduct(){
        System.out.println("=======================");
        System.out.println("+++++++제품 삭제++++++++");
        System.out.print("삭제할 제품명 입력: ");
        String del_name = scn.next(); // 삭제할 제품명 입력
        for(String key : productList.keySet()){
            if(key.equals(del_name)){
                Product p_info = productList.get(del_name); //HashMap에 저장되어 있는 정보 가져오기
                p_info.printValue(); // 제품 정보 출력
                System.out.println("---------------------");
                System.out.print("제품을 삭제하시겠습니까? y/n : ");
                String check = scn.next().trim(); // 삭제 유무 입력 받기
                if(check.equals("y") || check.equals("Y")){ // 삭제 O
                    Iterator<Product> iter = p.iterator();
                    while(iter.hasNext()){
                        Product product = iter.next();
                        if(product.getProduct_code() == productList.get(key).getProduct_code()){
                            iter.remove(); // ArrayList에 있는 정보 삭제
                            productList.remove(key); // HashMap에 있는 정보 삭제
                            break;
                        }
                    }
                    System.out.println("제품이 삭제되었습니다.");
                }else if (check.equals("n") || check.equals("N") ){ // 삭제 X
                    System.out.println("제품 삭제 취소.");
                    break;
                }else{ // y/n 이외의 키를 입력했을 때
                    System.out.println("잘못된 입력.");
                    break;
                }
                break;
            }
        }
        /*System.out.print("삭제할 제품 코드 입력: ");
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
                }else{
                    System.out.println("잘못된 입력.");
                }
                break;
            }else{
                System.out.println("제품이 존재하지 않습니다.");
            }
        }*/


    }
    // csv파일에 저장하기
    private void saveToFile() {
        String path = "product.csv";
        File file = new File(path); // 현재 파일이 저장되어 있는 위치에 파일 생성
        BufferedWriter writer = null;

        try {
            if(file.createNewFile()){ // 파일이 존재하지 않으면 생성
                System.out.println("File created: " + file.getName());
            }else { // 파일이 이미 존재하는 경우
                System.out.println("File already exists.");
            }

            writer = new BufferedWriter(new FileWriter(path, false)); // append의 값이 true면 이어붙이기 false면 덮어쓰기
            for(Product item : p){
                String str;
                // 값을 ','를 기준으로 구별하여 저장한다.
                str = item.getProduct_code() + "," + item.getProduct_name() + ","
                        + item.getPrice() + "," + item.getQuantity() + "\n";
                writer.append(str);
            }
            writer.close();

        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
