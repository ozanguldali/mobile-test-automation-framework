Feature: Android Feature

  Background:

    Given I use following capabilities:
      | udid | HVYDU19429004798 |
      | automationName| UiAutomator1 |
      |platformName|Android|
      |platformVersion|9|
      |deviceName|HUAWEI Mate 20 lite|
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

  @android
  Scenario: 1 - android test init scenario

    Given I use appium driver

    And I wait for 3 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver