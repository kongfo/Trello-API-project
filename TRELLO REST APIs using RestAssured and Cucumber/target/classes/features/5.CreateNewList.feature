@test
Feature:Create a new list
  Scenario:  Authorized memeber can Create a new list
    Given  Create a new list
    Then Save the list id in another variable
