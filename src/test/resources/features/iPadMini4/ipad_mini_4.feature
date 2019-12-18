@ignore
Feature: iPad Mini 4 Feature

  Background:

#    Given I set following key value pairs to context
#      | deviceTag | iPadMini4 |

    Given I use propertied capabilities for iOS device iPadMini4

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

  @ios @ignore
  Scenario: 1 - ios test init scenario

    Given I use appium driver

    And I wait for 5 seconds

    Then I close driver

    And I wait for 5 seconds

    And I use appium driver

    And I wait for 5 seconds

    Then I close driver