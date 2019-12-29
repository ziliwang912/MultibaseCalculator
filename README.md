# Multibase Calculator

## CICS 3120 Project One
This GUI multi-base calculator can do addition, subtraction, multiplication, and division 
with any base between 2 and 16. The slider lets user select the base the calculator is 
currently using. When the user uses the slider to select a different base, the calculator 
responds immediately: the numbers in the calculator's display area will be represented in
the new base, and the digit buttons will reflect the new base.

## Original Instructions
See the instructions at https://bc-cisc3120-s17.github.io/project1.

## Continued Project
There are potential bugs in this app. This is the continued develepment for this project.

## Bugs already known:
1. When changing slider, expression doesn't response accordingly sometimes; (FIXED)
2. Lack of inpout validation, expressions like "*67=" will throw exception;
3. This calc cannot handle expressions like "-2*4=";
4. This calc can only handle integer range calculation. (This is intended however)
