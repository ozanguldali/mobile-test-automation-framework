@deneme
Feature: Deneme Feature

  @ignore
  Scenario: 1 - android google test

    Given I use following capabilities:
      |platformName|Android|
      |platformVersion|9|
      |browserName|Chrome|
      |deviceName|127.0.0.1:5554|

    And I wait for 2 seconds

    Given I use chrome driver

    Then I open google page

  @ignore
  Scenario: 2 - android test test

    Given I use following capabilities:
      |platformName|Android|
      |platformVersion|9|
      |browserName|Chrome|
      |deviceName|127.0.0.1:5554|

    And I wait for 2 seconds

    Given I use chrome driver

    Then I open test page
