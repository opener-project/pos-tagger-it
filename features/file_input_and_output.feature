Feature: Using a italian tokenized file as input and a postagged file as an output
  In order to postag the file
  Using a file as an input
  Using a file as an output

  Scenario Outline: postag italian input file.
    If "<filename>" is empty it will not appear at KAF header.

    Given input file's language "<language>"
    Given the file name "<filename>"
    Given the fixture file "<input_file>"
    And I put them through the kernel
    Then the output should match the fixture "<output_file>"
  Examples:
    | language | input_file                  | output_file                  |
    | it       | italian_tokenized_input.kaf | italian_postagged_output.kaf |
