BTW Example taken from http://progopedia.com/language/lolcode/
BTW This program demonstrates LOLCode functions, loops within
BTW functions, basic math operations, and nested if statements
BTW by taking user input of three constants a, b, and c that 
BTW make up a quadratic and then solving the quadratic equation.

HAI QUADRATIC
  BTW Function definition
  HOW DUZ I SQRT YR X
    I HAS A X_N ITZ 10
    I HAS A LIMIT ITZ 100
    I HAS A COUNTER ITZ 0
	
	BTW Loop starts here
    IM IN YR LOOP UPPIN YR COUNTER WILE COUNTER SMALLR THAN LIMIT
      I HAS A TERM ITZ QUOSHUNT OF X AN X_N
      TERM R SUM OF X_N AN TERM
      TERM R QUOSHUNT OF TERM AN 2
      X_N R TERM
    IM OUTTA YR LOOP  BTW loop exits when counter >= limit
	
    FOUND YR X_N   BTW return statement
  IF U SAY SO      BTW function definition ends

  BTW prompt user for first constant
  VISIBLE "Enter a"
  I HAS A AC
  GIMMEH AC
  MAEK AC A NUMBR
  
  BTW If statement
  BOTH SAEM AC AN 0
  O RLY?
    YA RLY  BTW AC == 0
      VISIBLE "Not a quadratic equation."
    NO WAI  BTW AC != 0
      BTW prompt user for second constant
	  VISIBLE "Enter b"
      I HAS A BC
      GIMMEH BC
      MAEK BC A NUMBR
	  
	  BTW prompt user for third constant
	  VISIBLE "Enter c"
      I HAS A CC
      GIMMEH CC
      MAEK CC A NUMBR
	  
      I HAS A D ITZ DIFF OF PRODUKT OF BC AN BC  AN PRODUKT OF 4 AN PRODUKT OF AC AN CC
	  
	  BTW use a nested if statement with function calls to solve quadratic
      BOTH SAEM D AN 0
	  O RLY?
        YA RLY
          VISIBLE SMOOSH "x = " QUOSHUNT OF BC AN PRODUKT OF -2 AN AC MKAY
        NO WAI
          BOTH SAEM 0 AN SMALLR OF 0 AN D
		  O RLY?
            YA RLY
              VISIBLE SMOOSH "x1 = " QUOSHUNT OF SUM OF BC AN I CAN HAS SQRT D AN PRODUKT OF -2 AN AC MKAY
              VISIBLE SMOOSH "x2 = " QUOSHUNT OF DIFF OF BC AN I CAN HAS SQRT D AN PRODUKT OF -2 AN AC MKAY
            NO WAI
              D R PRODUKT OF D AN -1
              VISIBLE SMOOSH "x1 = (" QUOSHUNT OF BC AN PRODUKT OF -2 AN AC ", " QUOSHUNT OF I CAN HAS SQRT D AN PRODUKT OF -2 AN AC ")" MKAY
              VISIBLE SMOOSH "x2 = (" QUOSHUNT OF BC AN PRODUKT OF -2 AN AC ", " QUOSHUNT OF I CAN HAS SQRT D AN PRODUKT OF 2 AN AC ")" MKAY
          OIC
      OIC
  OIC
KTHXBYE
