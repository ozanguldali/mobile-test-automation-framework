@mi
Feature: mi phone Feature

  Background:

    Given I use following capabilities:
      | udid | b2592939 |
      | automationName| UiAutomator1 |
      |platformName|Android|
      |platformVersion|9|
      |deviceName|Mi Phone|
#      |bundleId      | com.pixerylabs.funimatetest |
      | appPackage   | com.pixerylabs.funimatetest |
      | appActivity  |  com.avcrbt.funimate.activity.StartActivity |
#      | appPackage   | com.android.vending                        |
#      | appActivity  |  com.android.vending.AssetBrowserActivity |

  @android
  Scenario: 0 - android test init scenario

    Given I use appium driver

    And I wait for 3 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver

  @android @ignore
  Scenario: 1 - android test init scenario

    Given I use appium driver

    And I wait for 3 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver