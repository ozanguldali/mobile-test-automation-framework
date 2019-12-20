@android
Feature: Deneme Feature

  Background:

#    Given I set following key value pairs to context
#      | deviceTag | iPhone8Plus |

    Given I use propertied capabilities for Android device P20Lite

    Given I use following capabilities:
      | appPackage   | com.pixerylabs.funimatetest |
      | appActivity  |  com.avcrbt.funimate.activity.StartActivity |

  @android @ignore
  Scenario: 0 scenario

    Given I use appium driver

    And I wait for 5 seconds

    And I take screenshot

    Then I close driver

    And I take screenshot

    And I open google page

#    And I wait for 3 seconds
#
#    And I use appium driver
#
#    And I wait for 5 seconds
#
#    Then I close driver

  @android
  Scenario: 1 scenario

    Given I use appium driver

    And I wait for 5 seconds

    And I take screenshot

    Then I run the app in background entirely

    And I take screenshot

    And I open avcr page