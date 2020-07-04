Feature: CreateForo

  Scenario: enter to application and create a foro
    Given I have a question
    When I create a foro and wait
    Then my question will have answers