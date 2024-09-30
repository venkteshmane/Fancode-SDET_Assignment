package com.FanCode.testcases;

import com.FanCode.base.base;
import com.FanCode.rest.RestResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.FanCode.utilities.constantUtil.getUsersEndpoint;

public class getUsersFromFancode_test extends base {

    @Test (enabled = true)
    public static List<Integer> getUsersOfFancode() throws JsonProcessingException {
        RestResource restResource = new RestResource(baseURL);

        Response getUsersFromFanCode = restResource.get(getUsersEndpoint, null, true)
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract()
                .response();

        String userResponse = getUsersFromFanCode.asString();
        JSONArray users = new JSONArray(userResponse);
        List<Integer> userID = new ArrayList<>();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            JSONObject address = user.getJSONObject("address");
            JSONObject geo = address.getJSONObject("geo");
            double lat = geo.getDouble("lat");
            double lng = geo.getDouble("lng");

            if (lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100) {
                int id = user.getInt("id");
                userID.add(id);
                System.out.println("UserId of the User from Fancode City with the matching geo coordinates: " + id);
                Assert.assertTrue(true);
            }
            if (userID.size() == 0) {
                System.out.println("No User is available from the Fancode city.");
                Assert.assertTrue(false);
            }
        }
        return userID;
    }
}