Feature: Category controller

  Scenario: Create and get category
    Given Created a category
    When Reade created category
    Then Got category should be as created

  Scenario: Create and delete category
    Given Created a category
    When Delete created category
    Then Try get return 404_Not_found
