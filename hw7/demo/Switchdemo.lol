HAI Switchdemo

BTW This program demonstrates the LOLCode switch construct by using
BTW switches, nested switches, and nested loops to create a menu system
BTW for user input.  The user has a choice of two modes, boolean mode or
BTW comparison mode.  Boolean mode tests LOLCode's boolean operations
BTW and comparison mode tests LOLCode's comparison operations.  Once
BTW the mode is selected, the menu prompts the user to enter two operands
BTW and then asks the user to choose an operation.

I HAS A CHOICE ITZ "b"
I HAS A VAR ITZ 0
 
IM IN YR LOOP UPPIN YR VAR WILE DIFFRINT CHOICE AN "q"
	VISIBLE "Please enter choice: (b)oolean mode or (c)omparison mode, (q) to quit:"
	GIMMEH CHOICE	

	CHOICE
	WTF?
		OMG "b"  BTW boolean mode
			IM IN YR LOOP UPPIN YR VAR WILE DIFFRINT CHOICE AN "e"
			    VISIBLE "Enter first operand (WIN or hit return for FAIL):"
			    GIMMEH OP1
			    MAEK OP1 A TROOF
			    VISIBLE "Enter second operand (WIN or hit return for FAIL):"
			    GIMMEH OP2
			    MAEK OP2 A TROOF
				VISIBLE "Please enter choice: (a)nd, (o)r, or (x)or, (e) to exit:"
				GIMMEH CHOICE
				
				CHOICE
				WTF?
					OMG "a"  BTW boolean AND operation
					    VISIBLE "AND:"
						VISIBLE BOTH OF OP1 AN OP2
						GTFO
					OMG "o"  BTW boolean OR operation
					    VISIBLE "OR:"
						VISIBLE EITHER OF OP1 AN OP2
						GTFO
					OMG "x"  BTW boolean XOR operation
					    VISIBLE "XOR:"
						VISIBLE WON OF OP1 AN OP2
						GTFO
					OMG "e"  BTW exit menu choice
						GTFO
					OMGWTF   BTW anything else is bad input
						VISIBLE "bad choice, enter again"
						GTFO
				OIC
			IM OUTTA YR LOOP
			GTFO
		OMG "c"  BTW comparison mode
			IM IN YR LOOP UPPIN YR VAR WILE DIFFRINT CHOICE AN "e"
		        VISIBLE "Enter first number:"
			    GIMMEH OP1
			    VISIBLE "Enter second number:"
			    GIMMEH OP2			
				VISIBLE "Please enter choice: (g)reater than, (l)ess than, (ge)greater than or equal"
				VISIBLE "(le)less than or equal, e(q)uals, or (n)ot equals, (e) to exit:"
				GIMMEH CHOICE
				
				CHOICE
				WTF?
					OMG "g"  BTW OP1 > OP2
						VISIBLE "GREATER THAN:"
						VISIBLE DIFFRINT OP1 AN SMALLR OF OP1 AN OP2
						GTFO
					OMG "l"  BTW OP1 < OP2
						VISIBLE "LESS THAN:"
						VISIBLE DIFFRINT OP1 AN BIGGR OF OP1 AN OP2
						GTFO
					OMG "ge" BTW OP1 >= OP2
						VISIBLE "GREATER THAN OR EQUAL:"
						VISIBLE BOTH SAEM OP1 AN BIGGR OF OP1 AN OP2
						GTFO
					OMG "le" BTW OP1 <= OP2
						VISIBLE "LESS THAN OR EQUAL:"
						VISIBLE BOTH SAEM OP1 AN SMALLR OF OP1 AN OP2
						GTFO
					OMG "q"  BTW OP1 == OP2
						VISIBLE "EQUALS:"
						VISIBLE BOTH SAEM OP1 AN OP2
						GTFO
					OMG "n"  BTW OP1!=OP2
						VISIBLE "NOT EQUALS:"
						VISIBLE DIFFRINT OP1 AN OP2
						GTFO
					OMG "e"  BTW exit menu choice
						GTFO
					OMGWTF   BTW anything else is bad input
						VISIBLE "bad choice, enter again"
						GTFO
				OIC
			IM OUTTA YR LOOP
			GTFO
		OMG "q"  BTW quit program
			GTFO
		OMGWTF   BTW anything else is bad input
		    VISIBLE "bad choice, enter again"
		    GTFO
	OIC 
		 
IM OUTTA YR LOOP

KTHXBYE