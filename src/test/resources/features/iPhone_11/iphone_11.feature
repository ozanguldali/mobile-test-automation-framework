@ignore
Feature: iPhone 11 Feature

  Background:

    Given I use following capabilities:
      | automationName| XCUITest |
      |platformName|iOS|
      |platformVersion|13.2|
#      |browserName|Safari|
      |deviceName|Pixery iOS - 005 |
      |udid      |00008030-0010059E1E90802E|
      |xcodeOrgId|9B7X2U2FRF |
      |xcodeSigningId| iPhone Developer |
      |bundleId      | com.avcr.impresso |
      | WDALOCALPORT   | 8201              |

  @ios
  Scenario: 0 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver

  @ios @ignore
  Scenario: 1 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    Then I close driver

    And I wait for 5 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver