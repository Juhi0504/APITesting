package nav;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NAV {

    public static Map<String , Map<String, HoldingData>> map;

    public static double calculateNAV()
    {
        System.out.println("Enter date in yyyymmdd  format");
        Scanner sc = new Scanner(System.in);
        String date = sc.nextLine();

        Double nav = 0.0d;

        if(map == null || map.isEmpty())
        {
            map = new HashMap<>();
        }

        String holdingAPI = "https://raw.githubusercontent.com/arcjsonapi/HoldingValueCalculator/master/api/holding";
        String holdingResponse = getAPI(holdingAPI);
        mapHoldingToResponse(holdingResponse);

        String pricesAPI = "https://raw.githubusercontent.com/arcjsonapi/HoldingValueCalculator/master/api/pricing";
        String priceResponse = getAPI(pricesAPI);
        mapPriceToResponse(priceResponse);

        String dateKey = date.replaceAll("[\\s\\-()]", "");

        System.out.println("Price on date " + date);
        if(map.containsKey(date))
        {
            Map<String , HoldingData> subMap = map.get(dateKey);

            for(Map.Entry<String, HoldingData> e : subMap.entrySet())
            {
                nav += e.getValue().getPrice() * e.getValue().getQuantity();
            }
        }
        return nav;
    }

    public static String getAPI(String urll)
    {
        try{
            URL url = new URL(urll);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("response code ="+responseCode);

            String inline = "";
            Scanner sc = new Scanner(url.openStream());

            while(sc.hasNext())
            {
                inline += sc.nextLine();
            }
            sc.close();

            return inline;

        } catch (IOException e) {
            System.out.println("exception code ="+e);
        }

        return "";
    }

    public static void mapHoldingToResponse(String holdingResponse)
    {
        try{
            Gson gson = new GsonBuilder().create();
            HoldingData[] hd = gson.fromJson(holdingResponse, HoldingData[].class);

            for(HoldingData h : hd)
            {
                if(map.containsKey(h.getDate()))
                {
                    map.get(h.getDate()).put(h.getSecurity(), h);
                }
                else
                {
                    Map<String , HoldingData> subMap = new HashMap<>();
                    subMap.put(h.getSecurity(), h);
                    map.put(h.getDate(),subMap);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("error in holding gson parsing =["+e+"]");
        }
    }

    public static void mapPriceToResponse(String pricesResponse)
    {
        try{
            Gson gson = new GsonBuilder().create();
            PriceData[] pr = gson.fromJson(pricesResponse, PriceData[].class);

            for(PriceData p : pr)
            {
                if(map.containsKey(p.getDate()) && map.get(p.getDate()).containsKey(p.getSecurity()))
                {
                    map.get(p.getDate()).get(p.getSecurity()).setPrice(p.getPrice());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("error in price gson parding =["+e+"]");
        }
    }
}
