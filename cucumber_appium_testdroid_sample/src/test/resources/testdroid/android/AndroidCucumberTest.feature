Feature: Example Android Test

@android @web
  Scenario: Make your decision
    Given I am at the main page
    When I click to use testdroid cloud
    And I insert my name to proceed
    Then I can submit my answer
