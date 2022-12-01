package reqres;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import nav.HoldingData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Processor {

    public static void FindFrequency()
    {
        String url = "https://reqres.in/api/users";
        JsonObject jsonObject = getAPIResponse(url);

        if(!jsonObject.isJsonNull())
        {
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");

            /* for converting to list
            List<User> users = new ArrayList<User>();

            Type listType = new TypeToken<List<User>>() {
            }.getType();
            users= new Gson().fromJson(jsonArray, listType);*/

            User[] users = new Gson().fromJson(jsonArray, User[].class);

            //System.out.println("User " + users.length + " " + users);

            int vowelCount = 0;
            int alphaCount  =0;
            String regex = "[aeiouAEIOU]";
            //Compiling the regular expression
            Pattern pattern = Pattern.compile(regex);

            for(int i=0; i<users.length; i++)
            {
                if(!users[i].getFirst_name().isEmpty())
                {
                    String c = String.valueOf(users[i].getFirst_name().charAt(0));
                    if(pattern.matcher(c).matches())
                    {
                        vowelCount++;
                    }
                    else
                    {
                        alphaCount++;
                    }
                }
            }

            System.out.println("Frequency of names starting with vowel: " + vowelCount);
            System.out.println("Frequency of names starting with consonant: " + alphaCount);
        }
    }

    public static JsonObject getAPIResponse(String urll)
    {
        try{
            URL url = new URL(urll);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("response code " + responseCode);

            String inline = "";
            Scanner sc = new Scanner(url.openStream());

            while(sc.hasNext())
            {
                inline += sc.nextLine();
            }
            sc.close();

            //System.out.println("inline " + inline);
            JsonObject jsonObject = new Gson().fromJson(inline, JsonObject.class);

            return jsonObject;
        }
        catch(IOException e)
        {
            System.out.println("exception code ="+e);
        }
        return null;
    }
}
