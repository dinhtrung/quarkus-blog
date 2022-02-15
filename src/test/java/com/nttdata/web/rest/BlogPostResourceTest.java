package com.nttdata.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.nttdata.TestUtil;
import com.nttdata.domain.BlogPost;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

import java.util.List;
import java.nio.ByteBuffer;
    import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@QuarkusTest
public class BlogPostResourceTest {

    private static final TypeRef<BlogPost> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<BlogPost>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FIGURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIGURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIGURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIGURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATE = 0;
    private static final Integer UPDATED_STATE = 1;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final LocalDate DEFAULT_PUBLISHED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISHED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    private static final Instant DEFAULT_LAST_MODIFIED_AT = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_LAST_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";



    String adminToken;

    BlogPost blogPost;


    @BeforeAll
    static void jsonMapper() {
        RestAssured.config =
            RestAssured.config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }




    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPost createEntity() {
        var blogPost = new BlogPost();
        blogPost.title = DEFAULT_TITLE;
        blogPost.summary = DEFAULT_SUMMARY;
        blogPost.figure = DEFAULT_FIGURE;
        blogPost.content = DEFAULT_CONTENT;
        blogPost.category = DEFAULT_CATEGORY;
        blogPost.slug = DEFAULT_SLUG;
        blogPost.state = DEFAULT_STATE;
        blogPost.weight = DEFAULT_WEIGHT;
        blogPost.publishedAt = DEFAULT_PUBLISHED_AT;
        blogPost.createdAt = DEFAULT_CREATED_AT;
        blogPost.lastModifiedAt = DEFAULT_LAST_MODIFIED_AT;
        blogPost.createdBy = DEFAULT_CREATED_BY;
        blogPost.lastModifiedBy = DEFAULT_LAST_MODIFIED_BY;
        blogPost.tags = DEFAULT_TAGS;
        return blogPost;
    }

    @BeforeEach
    public void initTest() {
        blogPost = createEntity();
    }

    @Test
    public void createBlogPost() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the BlogPost
        blogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate + 1);
        var testBlogPost = blogPostList.stream().filter(it -> blogPost.id.equals(it.id)).findFirst().get();
        assertThat(testBlogPost.title).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlogPost.summary).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testBlogPost.figure).isEqualTo(DEFAULT_FIGURE);
        assertThat(testBlogPost.content).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBlogPost.category).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testBlogPost.slug).isEqualTo(DEFAULT_SLUG);
        assertThat(testBlogPost.state).isEqualTo(DEFAULT_STATE);
        assertThat(testBlogPost.weight).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testBlogPost.publishedAt).isEqualTo(DEFAULT_PUBLISHED_AT);
        assertThat(testBlogPost.createdAt).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBlogPost.lastModifiedAt).isEqualTo(DEFAULT_LAST_MODIFIED_AT);
        assertThat(testBlogPost.createdBy).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBlogPost.lastModifiedBy).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testBlogPost.tags).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    public void createBlogPostWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the BlogPost with an existing ID
        blogPost.id = "1";

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        blogPost.title = null;

        // Create the BlogPost, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeTest);
    }
    @Test
    public void checkSlugIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        blogPost.slug = null;

        // Create the BlogPost, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeTest);
    }
    @Test
    public void checkStateIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        blogPost.state = null;

        // Create the BlogPost, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void updateBlogPost() {
        // Initialize the database
        blogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the blogPost
        var updatedBlogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts/{id}", blogPost.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the blogPost
        updatedBlogPost.title = UPDATED_TITLE;
        updatedBlogPost.summary = UPDATED_SUMMARY;
        updatedBlogPost.figure = UPDATED_FIGURE;
        updatedBlogPost.content = UPDATED_CONTENT;
        updatedBlogPost.category = UPDATED_CATEGORY;
        updatedBlogPost.slug = UPDATED_SLUG;
        updatedBlogPost.state = UPDATED_STATE;
        updatedBlogPost.weight = UPDATED_WEIGHT;
        updatedBlogPost.publishedAt = UPDATED_PUBLISHED_AT;
        updatedBlogPost.createdAt = UPDATED_CREATED_AT;
        updatedBlogPost.lastModifiedAt = UPDATED_LAST_MODIFIED_AT;
        updatedBlogPost.createdBy = UPDATED_CREATED_BY;
        updatedBlogPost.lastModifiedBy = UPDATED_LAST_MODIFIED_BY;
        updatedBlogPost.tags = UPDATED_TAGS;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedBlogPost)
            .when()
            .put("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        var testBlogPost = blogPostList.stream().filter(it -> updatedBlogPost.id.equals(it.id)).findFirst().get();
        assertThat(testBlogPost.title).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogPost.summary).isEqualTo(UPDATED_SUMMARY);
        assertThat(testBlogPost.figure).isEqualTo(UPDATED_FIGURE);
        assertThat(testBlogPost.content).isEqualTo(UPDATED_CONTENT);
        assertThat(testBlogPost.category).isEqualTo(UPDATED_CATEGORY);
        assertThat(testBlogPost.slug).isEqualTo(UPDATED_SLUG);
        assertThat(testBlogPost.state).isEqualTo(UPDATED_STATE);
        assertThat(testBlogPost.weight).isEqualTo(UPDATED_WEIGHT);
        assertThat(testBlogPost.publishedAt).isEqualTo(UPDATED_PUBLISHED_AT);
        assertThat(testBlogPost.createdAt).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBlogPost.lastModifiedAt).isEqualTo(UPDATED_LAST_MODIFIED_AT);
        assertThat(testBlogPost.createdBy).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBlogPost.lastModifiedBy).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBlogPost.tags).isEqualTo(UPDATED_TAGS);
    }

    @Test
    public void updateNonExistingBlogPost() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .put("/api/blog-posts")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the BlogPost in the database
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBlogPost() {
        // Initialize the database
        blogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the blogPost
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/blog-posts/{id}", blogPost.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var blogPostList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(blogPostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllBlogPosts() {
        // Initialize the database
        blogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the blogPostList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(blogPost.id))
            .body("title", hasItem(DEFAULT_TITLE))            .body("summary", hasItem(DEFAULT_SUMMARY.toString()))            .body("figure", hasItem(DEFAULT_FIGURE.toString()))            .body("content", hasItem(DEFAULT_CONTENT.toString()))            .body("category", hasItem(DEFAULT_CATEGORY))            .body("slug", hasItem(DEFAULT_SLUG))            .body("state", hasItem(DEFAULT_STATE.intValue()))            .body("weight", hasItem(DEFAULT_WEIGHT.doubleValue()))            .body("publishedAt", hasItem(TestUtil.formatDateTime(DEFAULT_PUBLISHED_AT)))            .body("createdAt", hasItem(TestUtil.formatDateTime(DEFAULT_CREATED_AT)))            .body("lastModifiedAt", hasItem(TestUtil.formatDateTime(DEFAULT_LAST_MODIFIED_AT)))            .body("createdBy", hasItem(DEFAULT_CREATED_BY))            .body("lastModifiedBy", hasItem(DEFAULT_LAST_MODIFIED_BY))            .body("tags", hasItem(DEFAULT_TAGS));
    }

    @Test
    public void getBlogPost() {
        // Initialize the database
        blogPost = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(blogPost)
            .when()
            .post("/api/blog-posts")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the blogPost
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/blog-posts/{id}", blogPost.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the blogPost
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts/{id}", blogPost.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(blogPost.id))
            
                .body("title", is(DEFAULT_TITLE))
                .body("summary", is(DEFAULT_SUMMARY.toString()))
                .body("figure", is(DEFAULT_FIGURE.toString()))
                .body("content", is(DEFAULT_CONTENT.toString()))
                .body("category", is(DEFAULT_CATEGORY))
                .body("slug", is(DEFAULT_SLUG))
                .body("state", is(DEFAULT_STATE.intValue()))
                .body("weight", is(DEFAULT_WEIGHT.doubleValue()))
                .body("publishedAt", is(TestUtil.formatDateTime(DEFAULT_PUBLISHED_AT)))
                .body("createdAt", is(TestUtil.formatDateTime(DEFAULT_CREATED_AT)))
                .body("lastModifiedAt", is(TestUtil.formatDateTime(DEFAULT_LAST_MODIFIED_AT)))
                .body("createdBy", is(DEFAULT_CREATED_BY))
                .body("lastModifiedBy", is(DEFAULT_LAST_MODIFIED_BY))
                .body("tags", is(DEFAULT_TAGS));
    }

    @Test
    public void getNonExistingBlogPost() {
        // Get the blogPost
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/blog-posts/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
