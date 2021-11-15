Feature: Weather Stations

  Scenario: Register a weather station
    Given Client perform post operation for end point "stations" with body
    |external_id|name|latitude|longitude|altitude|
    |SF_TEST001 |San Francisco Test Station|37.76|-122.43|150|
    Then Response body data "should" match per column with below column data below and have auto generated id
      |external_id|name|latitude|longitude|altitude|
      |SF_TEST001 |San Francisco Test Station|37.76|-122.43|150|
Scenario: Get the newly registered weather station info
  Given Client perform get operation for end point "stations/{id}"
  Then Response body data "should" match per column with below column data below and have auto generated id
    |external_id|name|latitude|longitude|altitude|
    |SF_TEST001 |San Francisco Test Station|37.76|-122.43|150|
  Scenario: Update the station info
    Given Client perform put operation for end point "stations/{id}" with body
      |external_id|name|latitude|longitude|altitude|
      |SP_TEST002 |Cape Town|33.9249|18.4241|100|
    Then Response body data "should" match per column with below column data below and have auto generated id
      |external_id|name|latitude|longitude|altitude|
      |SP_TEST002 |Cape Town|33.9249|18.4241|100|
    And Client perform get operation for end point "stations/{id}"
    Then Response body data "should" match per column with below column data below and have auto generated id
      |external_id|name|latitude|longitude|altitude|
      |SP_TEST002 |Cape Town|33.9249|18.4241|100|



