HAI Switchdemo

I HAS A CHOICE ITZ "b"
I HAS A VAR ITZ 0
 
IM IN YR LOOP UPPIN YR VAR WILE DIFFRINT CHOICE AN "q"
	VISIBLE "Please enter choice: (b)oolean mode or (c)omparison mode, (q) to quit:"
	GIMMEH CHOICE	

	CHOICE
	WTF?
		OMG "b"
			IM IN YR LOOP UPPIN YR VAR WILE DIFFRINT CHOICE AN "e"
			    VISIBLE "Enter first operand (WIN or FAIL):"
			    GIMMEH OP1
			    MAEK OP1 A TROOF
			    VISIBLE "Enter second operand (WIN or FAIL):"
			    GIMMEH OP2
			    MAEK OP2 A TROOF
				VISIBLE "Please enter choice: (a)nd, (o)r, or (x)or, (e) to exit:"
				GIMMEH CHOICE
				
				CHOICE
				WTF?
					OMG "a"
					    VISIBLE "AND:"
						VISIBLE BOTH OF OP1 AN OP2
						GTFO
					OMG "o"
					    VISIBLE "OR:"
						VISIBLE EITHER OF OP1 AN OP2
						GTFO
					OMG "x"
					    VISIBLE "XOR:"
						VISIBLE WON OF OP1 AN OP2
						GTFO
					OMG "e"
						GTFO
					OMGWTF
						VISIBLE "bad choice, enter again"
						GTFO
				OIC
			IM OUTTA YR LOOP
			GTFO
		OMG "c"
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
					OMG "g"
						VISIBLE "GREATER THAN:"
						VISIBLE DIFFRINT OP1 AN SMALLR OF OP1 AN OP2
						GTFO
					OMG "l"
						VISIBLE "LESS THAN:"
						VISIBLE DIFFRINT OP1 AN BIGGR OF OP1 AN OP2
						GTFO
					OMG "ge"
						VISIBLE "GREATER THAN OR EQUAL:"
						VISIBLE BOTH SAEM OP1 AN BIGGR OF OP1 AN OP2
						GTFO
					OMG "le"
						VISIBLE "LESS THAN OR EQUAL:"
						VISIBLE BOTH SAEM OP1 AN SMALLR OF OP1 AN OP2
						GTFO
					OMG "q"
						VISIBLE "EQUALS:"
						VISIBLE BOTH SAEM OP1 AN OP2
						GTFO
					OMG "n"
						VISIBLE "NOT EQUALS:"
						VISIBLE DIFFRINT OP1 AN OP2
						GTFO
					OMG "e"
						GTFO
					OMGWTF
						VISIBLE "bad choice, enter again"
						GTFO
				OIC
			IM OUTTA YR LOOP
			GTFO
		OMG "q"
			GTFO
		OMGWTF
		    VISIBLE "bad choice, enter again"
		    GTFO
	OIC 
		 
IM OUTTA YR LOOP

KTHXBYE