BTW Example taken from http://progopedia.com/language/lolcode/
BTW This program tests basic LOLCode loops by taking a number
BTW from user input and printing out the Fibonacci sequence
BTW up to that number.

HAI FIBONACCI
  I HAS A I ITZ 0
  I HAS A FIB1 ITZ 0
  I HAS A FIB2 ITZ 1
  BTW Take user input
  VISIBLE "Please enter the Fibonacci number you would like to see up to:"
  GIMMEH FIBCOUNT
  
  BTW The loop starts here
  IM IN YR LOOP UPPIN YR I TIL BOTH SAEM I AN FIBCOUNT
    VISIBLE SMOOSH FIB2 ", " MKAY
    I HAS A FIB3 ITZ SUM OF FIB1 AN FIB2
    FIB1 R FIB2
    FIB2 R FIB3
  IM OUTTA YR LOOP
  VISIBLE "..."
KTHXBYE
