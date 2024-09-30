package com.FanCode.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestResource {

    private RequestSpecBuilder specBuilder;

    private String baseURL;


    public RestResource(String baseURL) {
        specBuilder = new RequestSpecBuilder();
        this.baseURL = baseURL;
    }


    private void setReuqestContentType(String contentType) {
        if (contentType.equalsIgnoreCase("json")) {
            specBuilder.setContentType(ContentType.JSON);

        } else if (contentType.equalsIgnoreCase("xml")) {
            specBuilder.setContentType(ContentType.XML);

        } else if (contentType.equalsIgnoreCase("text")) {
            specBuilder.setContentType(ContentType.TEXT);
        }
    }

    private RequestSpecification createRequestSpec(Map<String, String> headersMap) {
        specBuilder.setBaseUri(baseURL);
        if (headersMap != null) {
            specBuilder.addHeaders(headersMap);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams) {
        specBuilder.setBaseUri(baseURL);
        if (headersMap != null) {
            specBuilder.addHeaders(headersMap);
        }
        if (queryParams != null) {
            specBuilder.addQueryParams(queryParams);
        }
        return specBuilder.build();
    }


    private RequestSpecification createRequestSpec(Object requestBody, String contentType) {
        specBuilder.setBaseUri(baseURL);
        setReuqestContentType(contentType);

        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap) {
        specBuilder.setBaseUri(baseURL);
        setReuqestContentType(contentType);
        if (headersMap != null) {
            specBuilder.addHeaders(headersMap);
        }
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, Map<String, String> queryParams) {
        specBuilder.setBaseUri(baseURL);
        setReuqestContentType(contentType);
        if (headersMap != null) {
            specBuilder.addHeaders(headersMap);
        }
        if (queryParams != null) {
            specBuilder.addQueryParams(queryParams);
        }
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }


    //Http Methods Utils:

    public Response get(String serviceUrl, Map<String, String> headersMap, boolean log) {

        if (log) {
            return RestAssured.given(createRequestSpec(headersMap)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(headersMap)).when().get(serviceUrl);
    }

    public Response get(String serviceUrl, Map<String, Object> queryParams, Map<String, String> headersMap, boolean log) {

        if (log) {
            return RestAssured.given(createRequestSpec(headersMap, queryParams)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(headersMap, queryParams)).when().get(serviceUrl);
    }


    public Response post(String serviceUrl, String contentType, Object requestBody, boolean log) {
        if (log) {
            return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType))
                .when()
                .post(serviceUrl);
    }


    public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log) {
        if (log) {
            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap))
                .when()
                .post(serviceUrl);
    }


    public Response put(String serviceUrl, String contentType, Object requestBody, boolean log) {
        if (log) {
            return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType))
                .when()
                .put(serviceUrl);
    }


    public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log) {
        if (log) {
            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap))
                .when()
                .put(serviceUrl);
    }

}
