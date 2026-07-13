package com.jsonplaceholder.api.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.ApiTestBase;
import io.restassured.http.ContentType;

/**
 * JSONPlaceholder API – GET and POST Tests
 *
 * Endpoints covered:
 *   GET  /posts         - Retrieve all posts
 *   GET  /posts/{id}    - Retrieve a single post by ID
 *   POST /posts         - Create a new post
 */
public class PostsGetAndPostTest extends ApiTestBase {

    public PostsGetAndPostTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        // baseURI is resolved automatically from config by ApiTestBase.
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void getAllPostsTest() {
        /*
         * Verify GET /posts returns 200 and a list of 100 posts.
         */
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()", equalTo(100))
            .log().all();
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void getPostByIdTest() {
        /*
         * Verify GET /posts/1 returns the correct post with expected field values.
         */
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id",     equalTo(1))
            .body("userId", equalTo(1))
            .body("title",  notNullValue())
            .body("body",   notNullValue())
            .log().all();
    }

    // ── Test #03 ──────────────────────────────────────────────────────────────
    @Test(priority = 3)
    public void createPostTest() {
        /*
         * Verify POST /posts creates a resource and returns 201 with the new resource ID.
         * JSONPlaceholder always returns id=101 for new posts.
         */
        JSONObject requestBody = new JSONObject();
        requestBody.put("title",  "Automation Post");
        requestBody.put("body",   "Created by Selenium Java Automation Framework");
        requestBody.put("userId", 1);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("id",     equalTo(101))
            .body("title",  equalTo("Automation Post"))
            .body("userId", equalTo(1))
            .log().all();
    }
}
