package Shop;

// 장바구니 데이터 클래스
public class Basket {
    // 장바구니ID, 고객ID, , 제품ID, 구매수량, 금액
    private int basketID, customerID, productID, quntityAll, price;
    private String date; //  구매날짜

    Basket(int basketID, int customerID, int productID, String date, int quntityAll, int price){
        this.basketID = basketID;
        this.customerID = customerID;
        this.productID = productID;
        this.quntityAll = quntityAll;
        this.date = date;
        this.price = price;
    }

    // setter
    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuntityAll(int quntityAll) {
        this.quntityAll = quntityAll;
    }

    //getter
    public int getBasketID() {
        return basketID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getProductID() {
        return productID;
    }

    public String getDate() {
        return date;
    }

    public int getQuntityAll() {
        return quntityAll;
    }

    public int getPrice() {
        return price;
    }
}
