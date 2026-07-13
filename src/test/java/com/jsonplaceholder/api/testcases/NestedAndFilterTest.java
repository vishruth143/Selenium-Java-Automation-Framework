package com.jsonplaceholder.api.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.ApiTestBase;
import io.restassured.http.ContentType;

/**
 * JSONPlaceholder API – Nested Resources and Query Filter Tests
 *
 * Endpoints covered:
 *   GET /posts/1/comments      - Nested route: comments for a specific post
 *   GET /comments?postId=1     - Filter comments by postId query param
 *   GET /posts?userId=1        - Filter posts by userId query param
 *   GET /users/{id}            - Retrieve a specific user
 *   GET /todos?userId=1        - Filter todos by userId query param
 */
public class NestedAndFilterTest extends ApiTestBase {

    public NestedAndFilterTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        // baseURI is resolved automatically from config by ApiTestBase.
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void getCommentsByNestedRouteTest() {
        /*
         * Verify GET /posts/1/comments returns 200, a list of 5 comments,
         * and each comment has a valid postId and email field.
         */
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts/1/comments")
        .then()
            .statusCode(200)
            .body("size()",        equalTo(5))
            .body("postId",        everyItem(equalTo(1)))
            .body("email",         everyItem(notNullValue()))
            .body("[0].name",      notNullValue())
            .body("[0].body",      notNullValue())
            .log().all();
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void filterCommentsByQueryParamTest() {
        /*
         * Verify GET /comments?postId=1 returns same 5 comments as the nested route,
         * confirming query param filtering works correctly.
         */
        given()
            .contentType(ContentType.JSON)
            .queryParam("postId", 1)
        .when()
            .get("/comments")
        .then()
            .statusCode(200)
            .body("size()",  equalTo(5))
            .body("postId",  everyItem(equalTo(1)))
            .log().all();
    }

    // ── Test #03 ──────────────────────────────────────────────────────────────
    @Test(priority = 3)
    public void filterPostsByUserIdTest() {
        /*
         * Verify GET /posts?userId=1 returns only posts belonging to user 1
         * and that all returned items have userId = 1.
         */
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", 1)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()",  equalTo(10))
            .body("userId",  everyItem(equalTo(1)))
            .log().all();
    }

    // ── Test #04 ──────────────────────────────────────────────────────────────
    @Test(priority = 4)
    public void getUserByIdTest() {
        /*
         * Verify GET /users/1 returns the correct user with required fields.
         */
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("id",               equalTo(1))
            .body("name",             equalTo("Leanne Graham"))
            .body("username",         equalTo("Bret"))
            .body("email",            notNullValue())
            .body("address.city",     notNullValue())
            .body("company.name",     notNullValue())
            .log().all();
    }

    // ── Test #05 ──────────────────────────────────────────────────────────────
    @Test(priority = 5)
    public void filterTodosByUserIdTest() {
        /*
         * Verify GET /todos?userId=1 returns 20 todos for user 1
         * and all items have userId = 1.
         */
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", 1)
        .when()
            .get("/todos")
        .then()
            .statusCode(200)
            .body("size()",   equalTo(20))
            .body("userId",   everyItem(equalTo(1)))
            .body("title",    everyItem(notNullValue()))
            .log().all();
    }
}
