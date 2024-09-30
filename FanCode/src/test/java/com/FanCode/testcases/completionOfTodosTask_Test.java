package com.FanCode.testcases;

import com.FanCode.base.base;
import com.FanCode.rest.RestResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.FanCode.testcases.getUsersFromFancode_test.getUsersOfFancode;
import static com.FanCode.utilities.constantUtil.getTodosEndpoint;

public class completionOfTodosTask_Test extends base {

    @Test(enabled = true)
    public void getTodos() throws JsonProcessingException {
        List<Integer> users = getUsersOfFancode();

        RestResource restResource = new RestResource(baseURL);
        Response todoTasks = restResource.get(getTodosEndpoint, null, true)
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract()
                .response();

        String todosResponse = todoTasks.asString();
        JSONArray tasks = new JSONArray(todosResponse);

        int totalTasks = 0;
        int completedTasks = 0;

        for (int user : users) {
            for (int j = 0; j < tasks.length(); j++) {
                JSONObject task = tasks.getJSONObject(j);

                if (task.getInt("userId") == user) {
                    totalTasks++;
                    if (task.getBoolean("completed")) {
                        completedTasks++;
                    }
                }
            }

            System.out.println("totalTask = " + totalTasks);
            System.out.println("CompletedTask = " + completedTasks);

            if (totalTasks > 0) {
                double completionPercentage = (double) completedTasks / totalTasks * 100;

                if (completionPercentage > 50) {
                    System.out.println("UserId: " + user + " has more than 50% tasks completed.");
                    Assert.assertTrue(true);
                } else {
                    System.out.println("UserId: " + user + " does not have more than 50% tasks completed.");
                    Assert.assertTrue(false);
                }
            }
            totalTasks = 0;
            completedTasks = 0;
        }
    }
}