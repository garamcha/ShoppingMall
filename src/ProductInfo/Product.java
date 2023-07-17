package ProductInfo;

// 제품 정보 클래스
public class Product {
    private int product_code; // 제품 코드
    private String product_name; // 제품명
    private int price;  // 가격
    private int quantity;   // 재고
    private String product_type; // 품목


    // 생성자
    public  Product(){}

    public Product(int product_code, String product_name, int price, int quantity, String p_type) {
        this.product_code = product_code;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.product_type = p_type;
    }

    // setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // getter
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

    public String getProduct_type() {
        return product_type;
    }

    // 제품 정보 전체 출력 함수
    public void printValue() {
        System.out.printf("%5d", this.product_code); // 제품 코드

        System.out.printf("%15s\t\t" , this.product_name); // 제품명

        System.out.printf(" %10d원\t", this.price); // 제품 가격

        System.out.printf(" %10d개\t", this.quantity ); // 제품 수량

        System.out.printf(" %15s\t\n", this.product_type); // 제품 품목
    }
}
