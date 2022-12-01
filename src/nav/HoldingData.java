package nav;

public class HoldingData {

    private String date;
    private String security;
    private Integer quantity;
    private Double price;
    private String portfolio;

    @Override
    public String toString(){
        return this.date;
    }


    public String getDate(){
        return date;
    }

    public String getSecurity(){
        return security;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public String getPortfolio(){
        return portfolio;
    }

    public void setDate(String data){
        this.date = data;
    }

    public void setSecurity(String security){
        this.security = security;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public void getPortfolio(String portfolio){
        this.portfolio = portfolio;
    }


    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }
}
