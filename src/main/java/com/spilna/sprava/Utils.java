package com.spilna.sprava;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.spilna.sprava.businesslogic.objects.Interest;
import com.spilna.sprava.model.PostRO;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivanov on 10.11.15.
 */
@Component
public class Utils {

    public String searchRegionByCity(String cityName, String token) {
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


    public Map<String, Long> getPercentOfInterestMap(List<PostRO> postROs, boolean withPercent) {
        long generalSize = postROs.size();
        long politcsSize=0;
        long musicSize=0;
        long other=0;
        for (PostRO postRO: postROs) {
            if (postRO.getInterest() != null) {
                if (postRO.getInterest().equals(Interest.POLITIC.name())) {
                    politcsSize ++;
                }else if (postRO.getInterest().equals(Interest.MUSIC.name())) {
                    musicSize ++;
                } else {
                    other++;
                }
            } else {
                other ++;
            }
        }
        Map<String, Long> stringLongMap = new HashMap<>();
        stringLongMap.put(Interest.POLITIC.name(), getPercent(generalSize, politcsSize, withPercent));
        stringLongMap.put(Interest.MUSIC.name(), getPercent(generalSize, musicSize, withPercent));
        stringLongMap.put(Interest.OTHER.name(), getPercent(generalSize, other, withPercent));

        return  stringLongMap;
    }

    private long getPercent (long general, long countOfInterest, boolean withPercent) {

            return withPercent ? countOfInterest * 100/general : countOfInterest;
    }
}
