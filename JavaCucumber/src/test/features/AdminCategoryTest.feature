Feature: Admin Category

  Scenario: Add category with missing name
    Given I want to add new category with out providing category name
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario: Add category with invalid logo extention
    Given I want to add new category with invalid logo extention
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario: Add category with non existent parent id
    Given I want to add new category with non existent parent id
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario: Add category with invalid date format on start date
    Given I want to add new category with invalid date format on start date
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario: Add category with invalid date format on end date
    Given I want to add new category with invalid date format on end date
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario:Add category with start date higher than end date
    Given I want to add new category with start date higher than end date
    When I add new category
    Then The add category should return HTTP status code 400 Bad Request

  Scenario:Add category
    When I add new category
    Then The add category should return HTTP status code 200 Ok

  Scenario: Get category list
    When I get list category
    Then The get category list should return HTTP status code 200 Ok

  Scenario: Admin get category detail with non existent category id
    When I get list category
    Then The get category list should return HTTP status code 200 Ok
    Given I want to get category details with non existent id
    When I get category details
    Then the get category details return 400 Bad Request

  Scenario: get category detail with existent category id
    When I get list category
    Then The get category list should return HTTP status code 200 Ok
    Given I want to get category details with existent id
    When I get category details
    Then the get category details return 200 Ok

  Scenario: Admin get parent category
    When I get list of parent category
    Then the get parent category list should return HTTP status code 200 Ok

    Scenario: Admin get subcategory with non existent parent category
      Given I want to get subcategory with non

