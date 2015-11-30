package com.spilna.sprava.businesslogic.utils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.spilna.sprava.businesslogic.enums.Interest;
import com.spilna.sprava.businesslogic.object.PostRO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivanov on 10.11.15.
 */
@Component
public class Utils {
    private RestTemplate restTemplate = new RestTemplate();

    private final long LIMIT_PERCENT = 50;

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
        String region = ((JsonArray) o).getJsonObject(0).getString("region");
        return region;
    }

    public String getAccessToken(String url) {
        String accessToken = StringUtils.EMPTY;
        try {
            accessToken = restTemplate.getForObject(new URI(url), String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(accessToken)) {
            accessToken = accessToken.substring(accessToken.indexOf("=") + 1,accessToken.indexOf("&"));
        }
        return accessToken;
    }

    public List<Post> getListOfPostsFromFB (String token, String appSecret) {
        /**
         *
         * DefaultFacebookClient is the FacebookClient implementation
         *  that ships with RestFB. You can customize it by passing in
         * custom JsonMapper and WebRequestor implementations, or simply
         * write your own FacebookClient instead for maximum control.
         */
        FacebookClient facebookClient = new DefaultFacebookClient(token, appSecret, Version.VERSION_2_4);
        List<com.restfb.types.Post> postList = facebookClient.fetchConnection("me/feed", com.restfb.types.Post.class, Parameter.with("fields",
                "description,message")).getData();

        return postList;
    }

    public User getUserFromFB (String token, String appSecret) {
        /**
         *
         * DefaultFacebookClient is the FacebookClient implementation
         *  that ships with RestFB. You can customize it by passing in
         * custom JsonMapper and WebRequestor implementations, or simply
         * write your own FacebookClient instead for maximum control.
         */
        FacebookClient facebookClient = new DefaultFacebookClient(token, appSecret, Version.VERSION_2_4);
        User user = facebookClient.fetchObject("me", User.class);

        return user;
    }

    public FacebookType publishPostOnFB (String token, String postText) {

        DefaultFacebookClient fbClient = new DefaultFacebookClient(token);
        FacebookType publishMessageResponse = fbClient.publish("me/feed",
                FacebookType.class, Parameter.with("message", postText));

        return publishMessageResponse;
    }

    public Map<String, Long> getPercentOfInterestMap(List<PostRO> postROs, boolean withPercent) {
        long generalSize = postROs.size();
        long politcsSize = 0;
        long musicSize = 0;
        long other = 0;
        long newsSize = 0;
        long gamesSize=0;
        long sportSize=0;
        long scienceSize=0;
        long businessSize=0;
        long cinemaSize=0;
        long humorSize=0;
        for (PostRO postRO : postROs) {
            if (postRO.getInterest() != null) {
                if (postRO.getInterest().equals(Interest.POLITIC.name())) {
                    politcsSize++;
                } else if (postRO.getInterest().equals(Interest.MUSIC.name())) {
                    musicSize++;
                }
                else if (postRO.getInterest().equals(Interest.CINEMA.name())) {
                    cinemaSize++;
                }
                else if (postRO.getInterest().equals(Interest.SCIENCE.name())) {
                    scienceSize++;
                }
                else if (postRO.getInterest().equals(Interest.BUSINESS.name())) {
                    businessSize++;
                }
                else if (postRO.getInterest().equals(Interest.HUMOR.name())) {
                    humorSize++;
                }
                else if (postRO.getInterest().equals(Interest.GAMES.name())) {
                    gamesSize++;
                }
                else if (postRO.getInterest().equals(Interest.NEWS.name())) {
                    newsSize++;
                }
                else if (postRO.getInterest().equals(Interest.SPORT.name())) {
                    sportSize++;
                }
                else {
                    other++;
                }
            } else {
                other++;
            }
        }
        Map<String, Long> stringLongMap = new HashMap<>();
        stringLongMap.put(Interest.POLITIC.name(), getPercent(generalSize, politcsSize, withPercent));
        stringLongMap.put(Interest.MUSIC.name(), getPercent(generalSize, musicSize, withPercent));
        stringLongMap.put(Interest.CINEMA.name(), getPercent(generalSize, cinemaSize, withPercent));
        stringLongMap.put(Interest.SCIENCE.name(), getPercent(generalSize, scienceSize, withPercent));
        stringLongMap.put(Interest.SPORT.name(), getPercent(generalSize, sportSize, withPercent));
        stringLongMap.put(Interest.HUMOR.name(), getPercent(generalSize, humorSize, withPercent));
        stringLongMap.put(Interest.GAMES.name(), getPercent(generalSize, gamesSize, withPercent));
        stringLongMap.put(Interest.BUSINESS.name(), getPercent(generalSize, businessSize, withPercent));
        stringLongMap.put(Interest.NEWS.name(), getPercent(generalSize, newsSize, withPercent));
        stringLongMap.put(Interest.OTHER.name(), getPercent(generalSize, other, withPercent));

        return stringLongMap;
    }

    private long getPercent(long general, long cout, boolean withPercent) {
        return withPercent ? cout * 100 / general : cout;
    }

    public Interest getInterestByExistsInterestInPost(String postText, List<PostRO> postROList) {
        if (!CollectionUtils.isEmpty(postROList) && !StringUtils.isEmpty(postText)) {
            for (PostRO postRO : postROList) {
                if (!StringUtils.isEmpty(postRO.getPost()) && !StringUtils.isEmpty(postRO.getInterest())) {
                    if (isLooksLike(postText, postRO.getPost()) && !Interest.OTHER.name().equals(postRO.getInterest())) {
                        return Interest.valueOf(postRO.getInterest());
                    }
                }
            }
        }
        return Interest.OTHER;
    }

    private boolean isLooksLike(String postText, String postWithInterest) {
        if (postText.length() < 5) {
            return false;
        }
        if (postText.equalsIgnoreCase(postWithInterest)) {
            return true;
        }
        if (postWithInterest.contains(postText)) {
            return true;
        }
        String[] splitString = postWithInterest.split(" ");
        if (getPercent(splitString.length, getEqualsCount(splitString, postText), true) >= LIMIT_PERCENT) {
            return true;
        }
        String[] splitStringByComma = postWithInterest.split(",");
        if (getPercent(splitString.length, getEqualsCount(splitStringByComma, postText), true) >= LIMIT_PERCENT) {
            return true;
        }
        char[] postCharArray = postText.toCharArray();
        long count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : postCharArray) {
            if (postWithInterest.contains(String.valueOf(character))) {
                count++;
            }
            stringBuilder.append(String.valueOf(character));
            if (stringBuilder.length() == 3) {
                if(postWithInterest.contains(stringBuilder.toString())){
                    count++;
                }
                stringBuilder = new StringBuilder();
            }
        }
        if (getPercent(Long.valueOf(postText.length()), count/2, true) >= LIMIT_PERCENT) {
            return true;
        }
        return false;
    }

    private long getEqualsCount(String[] splitString, String postText) {
        long equalsCount = 0;
        for (String str : splitString) {
            if (postText.contains(str)) {
                equalsCount++;
            }
        }
        return equalsCount;
    }
}
