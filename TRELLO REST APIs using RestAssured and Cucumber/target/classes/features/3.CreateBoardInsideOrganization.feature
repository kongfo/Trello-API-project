@test
Feature: Create a board inside an organization

  Scenario: Authorized memeber can create a board inside an organization
    Given Create a board inside the organization
    Then Get the board  id and save it in a variable