package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class Hooks {

    static String baseURL = "https://api.trello.com";
    static String APIKey = "9ecd000e46713f89174631bff566fe45";
    static String APIToken = "ATTA3b0a787b2bd99ea9cf476484f62477f0384d4ca11014b8e7cc6bd170a8c8b190C11980C0";
    static String memberId = "";
    public String organizationId = "";
    static String listId = "";
    static String boardId = "";
    static String sl = "HTTP/1.1 200 OK";
    static int sc=200;
    static String OrgName = "New Organization 5";
    static String BoardName = "New Board 5";
    static String ListName = "New List 1";



    @Given("create a new organization")
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
        System.out.println(organizationId);
        response.prettyPrint();


    }

    @Then("Get Organizations for a member")
    public void getOrganizations() throws PendingException {

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
    }

    @Then("Get the board  id and save it in a variable")
    public void getBoardId() throws PendingException {

    }

}
