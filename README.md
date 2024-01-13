# Math calculator problem in Java

## Contents:
- [Problem statement](#Problem-statement)
- [List of subtasks](#List-of-subtasks)
- [Brief description of the solution](#Brief-description-of-the-solution)
- [Few words about structure](#Few-words-about-structure)

---

## Problem statement
It is needed to implement a console application that:
1) Reads data from an input file.
2) Processes the received information.
3) Writes the data to an output file.

The input and output files can be in the following formats: txt, xml, json. Additionally, the input and output files can be archived (zip, rar) and encrypted in various sequences (only archived, only encrypted, first archived then encrypted, and vice versa).

The task is to find all arithmetic operations in the input file and replace them with the results in the output file. Calculations should be performed in three ways:
- Regular expressions
- Reverse Polish notation
- API

Add a UI:
- CLI
- GUI
  
Add Web functionality and combine everything together: UI and Web.

---

## List of subtasks
- [x] Add a MathExpression class to store read data
- [x] Add a Result class to store results
- [x] Add a regex calculator
- [x] Add a reverse polish notation calculator
- [x] Add an API calculator
- [x] Add a text writer
- [x] Add a text Reader
- [x] Add a text Buffered Reader
- [x] Add a json API writer
- [x] Add a json non API writer
- [x] Add a json API Reader
- [x] Add a json non API Reader
- [x] Add an XML API writer
- [x] Add an XML non API writer
- [x] Add a XML API Reader
- [x] Add a XML non API Reader
- [x] Add a zip dearchiver
- [x] Add a zip archiver
- [x] Add a rar dearchiver
- [x] Add a rar archiver
- [x] Add an encoder
- [x] Add a decoder
- [X] Add an automatic format checker
- [X] Cover all code with unit-tests (Coverage is 92%)
- [X] Add a standardized exception handling
- [ ] Add a design patterns
- [x] Add a CLI
- [ ] Add a GUI
- [ ] Combine GUI and CLI
- [ ] Add a Web
- [ ] Combine UI and Web

---

## Brief description of the solution
The solution offers cyclic reading of multiple files, their dearchiving and decoding. All the dearchived and decoded files remain in the system in the same directory as the archived/encoded files, as temporary files, providing access to read the contents of these files. Upon the program's completion, all temporary files will be destroyed.

Upon successfully reading a file, its content will be displayed to the user for review. After reading, the user will be prompted to choose the calculation method, make a record to a file, as well as cyclic archiving and encoding. Upon successful recording, the contents of the reading buffer will be cleared.

It is possible to freely switch between reading/writing modes and create various sets of data and results.

---

## Few words about structure
All readers form this inheritance structure (And Writers have a similar structure):

![Local Image](images/scheme.png)