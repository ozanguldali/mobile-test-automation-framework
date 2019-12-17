@iPhone
Feature: Deneme Feature

  Background:

#    Given I set following key value pairs to context
#      | deviceTag | iPhone8Plus |

    Given I use propertied capabilities for iOS device iPhone8Plus

    Given I use following capabilities:
      | bundleId | com.avcr.impressodev |

  @ios
  Scenario: 0 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    Then I close driver

    And I wait for 3 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver