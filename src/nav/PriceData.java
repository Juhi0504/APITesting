package nav;

public class PriceData {

    private String date;
    private String security;
    private Double price;


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

    public void setDate(String data){
        this.date = data;
    }

    public void setSecurity(String security){
        this.security = security;
    }


    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }
}
