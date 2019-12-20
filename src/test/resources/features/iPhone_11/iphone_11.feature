@ios
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

  @ios @ignore
  Scenario: 0 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    And I take screenshot

    Then I close driver

    And I take screenshot

    And I open google page

  @ios @ignore
  Scenario: 1 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    And I take screenshot

    Then I run the app in background entirely

    And I take screenshot

    And I open avcr page

  @ios @ignore
  Scenario: 1 - crash ss test

    Given I use appium driver

    And I wait for 5 seconds

    And I take screenshot

    And I wait for 13 seconds

    And I take screenshot

    Given I use appium driver

    And I take screenshot

    Then I run the app in background entirely

    And I take screenshot

    And I open avcr page