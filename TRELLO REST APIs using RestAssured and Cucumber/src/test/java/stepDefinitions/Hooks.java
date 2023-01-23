package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;


public class Hooks {

    static String baseURL = "https://api.trello.com";
    static String APIKey = "9ecd000e46713f89174631bff566fe45";
    static String APIToken = "ATTA3b0a787b2bd99ea9cf476484f62477f0384d4ca11014b8e7cc6bd170a8c8b190C11980C0";
    static String memberId = "";
    public String organizationId;
    static String listId = "";
    static String boardId = "";
    static String sl = "HTTP/1.1 200 OK";
    static int sCode=200;
    static String sLine ="HTTP/1.1 200 OK";
    static String OrgName = "New Organization 5";
    static String BoardName = "New Board 5";
    static String ListName = "New List 1";


    @Given("create a new organization")
    public void createNewOrganiazation() throws PendingException {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                basePath("/1/organizations").
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                queryParam("displayName", OrgName).
                body("").
                when().
                post();

        JsonPath path = response.jsonPath();
        organizationId = path.getString("id");
        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);
        response.then().body("displayName", Matchers.equalTo(OrgName));

    }

    @Then("Get Organizations for a member")
    public void getOrganizationsForAMember() throws PendingException {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                basePath("/1/members/me").
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                when().
                get();

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @And("Create a board inside an organization")
    public void createABoardInsideAnOrganization() throws PendingException {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                basePath("/1/boards").
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                queryParam("idOrganization", organizationId).
                queryParam("name", BoardName).
                body("").
                when().
                post();

        JsonPath path = response.jsonPath();
        boardId = path.getString("id");

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @Then("Get boards in an organization")
    public void GetBoardsInAnOrganization() throws PendingException {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                pathParam("organizationId", organizationId).
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                when().
                get("/1/organizations/{organizationId}/boards");


        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }


    @And("Create a new list")
    public void createANewList() throws PendingException {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                basePath("/1/lists").
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                queryParam("idBoard", boardId).
                queryParam("name", ListName).
                body("").
                when().
                post();

        JsonPath path = response.jsonPath();
        listId = path.getString("id");
        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @Then("Get all lists on a board")
    public void getAllListsOnABoard() {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                basePath("/1/boards/" + boardId + "/lists").
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                when().
                get();

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @Then("Archive a list")
    public void archiveAList() {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                queryParam("value", "true").
                pathParam("id", listId).
                body("").
                when().
                put("/1/lists/{id}/closed");

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @Then("Delete a board")
    public void deleteABoard() {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                pathParam("boardId", boardId).
                body("").
                when().
                delete("/1/boards/{boardId}");

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);
    }

    @Then("Delete an organization")
    public void deleteAnOrganization() {
        Response response = RestAssured.
                given().
                baseUri(baseURL).
                queryParam("key", APIKey).
                queryParam("token", APIToken).
                pathParam("organizationId",organizationId).
                body("").
                when().
                delete("/1/organizations/{organizationId}");

        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);
    }

  /*  @Given("create a new organization")
    public void createNewOrganiazation() throws PendingException {
        RestAssured.baseURI= baseURL;
        RestAssured.basePath="/1/organizations";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",APIKey);
        requestSpecification.queryParam("token",APIToken);
        requestSpecification.queryParam("displayName",OrgName);
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.post();
        JsonPath path = response.jsonPath();
        organizationId = path.getString("id");
        System.out.println(organizationId);
        response.prettyPrint();

        response.then().statusCode(sCode);
        response.then().statusLine(sLine);
        this.organizationId=organizationId;
    }

    @Then("Get the organization id and save it in a variable")
    public void getOrganizationId() throws PendingException {


        System.out.println(organizationId);


    }

    @Given("get your member id")
    public void getMemberid() throws PendingException {

        RestAssured.baseURI= baseURL;
        RestAssured.basePath="/1/members/me";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",APIKey);
        requestSpecification.queryParam("token",APIToken);
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.get();
        JsonPath path = response.jsonPath();
        memberId = path.getString("id");
        System.out.println(memberId);
        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }

    @Then("Get Organizations for a member")
    public void getOrganizations() throws PendingException {
        RestAssured.baseURI= baseURL;
        RestAssured.basePath="/1/members/"+memberId+"/organizations";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",APIKey);
        requestSpecification.queryParam("token",APIToken);
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.get();
        System.out.println(organizationId);
        System.out.println(memberId);
        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);



    }

    @Given("Create a board inside the organization")
    public void createABoardInsideAnOrganization() throws PendingException {

        RestAssured.baseURI= baseURL;
        RestAssured.basePath="/1/boards/";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",APIKey);
        requestSpecification.queryParam("token",APIToken);
        requestSpecification.queryParam("name",BoardName);
        requestSpecification.queryParam("idOrganization",organizationId);
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.post();
        JsonPath path = response.jsonPath();
        boardId = path.getString("id");
        System.out.println(boardId);
        System.out.println(organizationId);

        response.prettyPrint();

        response.then().statusCode(sCode);
        response.then().statusLine(sLine);
    }

    @Then("Get the board  id and save it in a variable")
    public void getBoardId() throws PendingException {

    }


    @Given("Get boards in an organization")
    public void getboards() throws PendingException {

        RestAssured.baseURI= baseURL;
        RestAssured.basePath="/1/organizations/"+organizationId+"boards";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",APIKey);
        requestSpecification.queryParam("token",APIToken);
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.get();
        System.out.println(organizationId);
        response.prettyPrint();
        response.then().statusCode(sCode);
        response.then().statusLine(sLine);

    }



    @Given("Delete an organization")
    public void DeleteOrganization() throws PendingException {
        this.organizationId=organizationId;

        System.out.println();


    }*/
}
