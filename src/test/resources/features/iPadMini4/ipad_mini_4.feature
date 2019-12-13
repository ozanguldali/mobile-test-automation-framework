@ipad
Feature: iPad Mini 4 Feature

  Background:

    Given I use following capabilities:
      | automationName| XCUITest |
      |platformName|iOS|
      |platformVersion|11.1|
#      |browserName|Safari|
      |deviceName|Pixery's iPad mini 4|
      |udid      |a030962256bbc20c2f0340252dd6ccabad7a275d|
      |xcodeOrgId|9B7X2U2FRF                              |
      |xcodeSigningId| iPhone Developer                    |
      |bundleId      | com.avcr.impressodev                |

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