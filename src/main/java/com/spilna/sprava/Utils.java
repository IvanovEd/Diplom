package com.spilna.sprava;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivanov on 10.11.15.
 */
public class Utils {

    public static String searchRegionByCity(String cityName, String token) {
        String urlForSearch = "https://graph.facebook.com/v2.5/search?type=adgeolocation&q=" + cityName
                + "&access_token=" + token;
        RestTemplate restTemplate = new RestTemplate();
        String result = null;
        try {
            result = restTemplate.getForObject(new URI(urlForSearch), String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = new JsonObject(result);
        Object o = jsonObject.get("data");
        String region = ((JsonArray)o).getJsonObject(0).getString("region");
        return region;
    }
}
