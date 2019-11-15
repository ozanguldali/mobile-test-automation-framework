@deneme
Feature: Deneme Feature

  @ios
  Scenario: 0 - ios test init scenario

#    Given I start appium server for:
#      |host|127.0.0.1|
#      |port|4723     |
#      |platformName|Android|
#      |platformVersion|9|
#      |browserName|Chrome|
#      |deviceName|127.0.0.1:5554|

    Given I use following capabilities:
      | automationName| XCUITest |
      |platformName|iOS|
      |platformVersion|11.4|
#      |browserName|Safari|
      |deviceName|Pixery's iPhone 7|
      |udid      |93529a350f1de8ec849c00259cb51d83485b7116|
      |xcodeOrgId|9B7X2U2FRF                              |
      |xcodeSigningId| iPhone Developer                    |
      |bundleId      | com.avcr.impressodev                |

    And I wait for 2 seconds

    Given I use appium driver

    And I wait for 5 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver

#    Then I open amazon page

#    And I close appium server

  @android
  Scenario: 0 - android test init scenario

#    Given I start appium server for:
#      |host|127.0.0.1|
#      |port|4723     |
#      |platformName|Android|
#      |platformVersion|9|
#      |browserName|Chrome|
#      |deviceName|127.0.0.1:5554|

    Given I use following capabilities:
      | udid | HVYDU19429004798 |
      | automationName| UiAutomator2 |
      |platformName|Android|
      |platformVersion|9|
      |deviceName|HUAWEI Mate 20 lite|
#      |bundleId      | com.pixerylabs.funimatetest |
      | appPackage   | com.pixerylabs.funimatetest |
      | appActivity  |  com.avcrbt.funimate.activity.StartActivity |
#      | appPackage   | com.android.vending                        |
#      | appActivity  |  com.android.vending.AssetBrowserActivity |

    And I wait for 2 seconds

    Given I use appium driver

    And I wait for 3 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver

  Scenario: 1 - android google test

    Given I use following capabilities:
      |platformName|Android|
      |platformVersion|9|
      |browserName|Chrome|
      |deviceName|127.0.0.1:5554|

    And I wait for 2 seconds

    Given I use chrome driver

    Then I open google page

  Scenario: 2 - android test test

    Given I use following capabilities:
      |platformName|Android|
      |platformVersion|9|
      |browserName|Chrome|
      |deviceName|127.0.0.1:5554|

    And I wait for 2 seconds

    Given I use chrome driver

    Then I open test page
