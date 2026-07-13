package com.jsonplaceholder.api.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.ApiTestBase;
import io.restassured.http.ContentType;

/**
 * JSONPlaceholder API – PUT, PATCH, and DELETE Tests
 *
 * Endpoints covered:
 *   PUT    /posts/{id}  - Full resource update
 *   PATCH  /posts/{id}  - Partial resource update
 *   DELETE /posts/{id}  - Delete a resource
 */
public class PostsPutPatchDeleteTest extends ApiTestBase {

    public PostsPutPatchDeleteTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        // baseURI is resolved automatically from config by ApiTestBase.
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void updatePostFullTest() {
        /*
         * Verify PUT /posts/1 performs a full update and returns 200
         * with the updated field values in the response body.
         */
        JSONObject requestBody = new JSONObject();
        requestBody.put("id",     1);
        requestBody.put("title",  "Updated Title");
        requestBody.put("body",   "Updated body content");
        requestBody.put("userId", 1);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .put("/posts/1")
        .then()
            .statusCode(200)
            .body("id",    equalTo(1))
            .body("title", equalTo("Updated Title"))
            .body("body",  equalTo("Updated body content"))
            .log().all();
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void updatePostPartialTest() {
        /*
         * Verify PATCH /posts/1 performs a partial update and returns 200
         * with only the patched field changed.
         */
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "Patched Title Only");

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .patch("/posts/1")
        .then()
            .statusCode(200)
            .body("title", equalTo("Patched Title Only"))
            .body("id",    equalTo(1))
            .log().all();
    }

    // ── Test #03 ──────────────────────────────────────────────────────────────
    @Test(priority = 3)
    public void deletePostTest() {
        /*
         * Verify DELETE /posts/1 returns 200 and an empty response body.
         * JSONPlaceholder returns 200 (not 204) with an empty object {}.
         */
        given()
        .when()
            .delete("/posts/1")
        .then()
            .statusCode(200)
            .body(equalTo("{}"))
            .log().all();
    }
}
