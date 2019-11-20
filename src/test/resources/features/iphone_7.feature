@iphone
Feature: iPhone 7 Feature

  Background:

    Given I use following capabilities:
      | automationName| XCUITest |
      |platformName|iOS|
      |platformVersion|11.4|
#      |browserName|Safari|
      |deviceName|Pixery's iPhone 7|
      |udid      |93529a350f1de8ec849c00259cb51d83485b7116|
      |xcodeOrgId|9B7X2U2FRF                              |
      |xcodeSigningId| iPhone Developer                    |
      |bundleId      | com.avcr.impresso                |

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