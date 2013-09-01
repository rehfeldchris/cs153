(*
 [x] Use enumerated types for sex and jobClass
 [x] Use records
 [x] Use binary search tree
 [x] Use recursive add()
 [x] Pointer types
 [ ] Use set operations on enums (???)

 I think my binary search tree will work once I/we can figure out how to get the
 nodes to stay put after editing. every time add() is called it gets an empty tree. 
 
 My latest attempt to correct this is to have the add function return a pointer
 to the same node that was passed in, which hopefully gets updated. Still isn't working.
 It somehow inserts the node before making the branching comparison because it outputs
	"Took left path: 7 > 7" even though 7 hadn't been inserted yet. wtf
 
 This is the worst programming language I have ever used. Also Lazarus sucks. I've 
 resorted to using fpc.exe on the command line. 

 - Michael
*)
program EmployeeRecordReader;

type
    SexType = (M, F);
    JobClassType = (Factory, Office, Supervisor, VP, President);

    EmployeePtr = ^Employee;
    Employee = Record
                 number : integer;
		         lastName : string[8];
		         initials : string[2];
		         birthday : string[8];
		         department : string[4];
		         sex : SexType;
                 jobClass : JobClassType;
	        end;

    NodePtr = ^Node;
    Node = Record
             data : EmployeePtr;
	         left, right : NodePtr;
           end;

(* Recursively add employee e to the tree pointed to by node 
   Return an updated pointer to node with any modifications *)
function add(var e : EmployeePtr; node : NodePtr) : NodePtr;
begin
	writeln('add procedure');
	(* if (node = NIL) then  -- compiler bitches about this? *)
	(* I tried basing it off http://stackoverflow.com/questions/13787895/binary-tree-pascal-vs-c *)
    if (node^.data = NIL) then
        begin	
		write('node has no data, writing:');
		writeln(e^.number);
		
		New(node); (* this doesn't seem to make a difference either way *)
        node^.data := e;
        node^.left := NIL;
        node^.right := NIL;
		add := node;
        end
    else if (e^.number < node^.data^.number) then
		begin
		write('Took left path: ');
		write(e^.number);
		write(' < ');
		writeln(node^.data^.number);
		
        add := add(e, node^.left);
		end
    else
		begin
		write('Took left path: ');
		write(e^.number);
		write(' > ');
		writeln(node^.data^.number);
		
        add := add(e, node^.right)
		end
end;

(* Print binary tree in sorted order (inorder traversal) *)
procedure printTree(var node : NodePtr);
begin
    if node <> NIL then
    begin
    (* This is where you would do the tabular output stuff *)
	(* crashes since node is nil when it gets passed for some reason *)
        printTree(node^.left);
        write(node^.data^.number);
        printTree(node^.right);
    end;
end;

(* Find a specific employee by number in the tree pointed to by node
   Not sure if we even need this for the assignment but it's there *)
function find(var number : integer; node : NodePtr) : NodePtr;
begin
    if node^.data <> NIL then
    begin
         if (number < node^.data^.number) then
	    find := find(number, node^.left)
	 else if (number > node^.data^.number) then
	    find := find(number, node^.right)
	 else
	    find := node (* found the right record *)
    end
    else 
	    find := NIL (* Return NIL if not found *)
end;

(* Variables for main procedure *)
var
	(* Binary search tree comprised of nodes that have a data field of type Employee *)
    employeesPtr : NodePtr;
    employees : Node;(*(data : NIL; left: NIL; right: NIL);*)
	
	(* Placeholder employee -- overwrite this data *)
    tempEmployeePtr : EmployeePtr;
    tempEmployee : Employee =  (number : 0; lastName : 'Argh'; initials : 'AR';
               birthday : '20130101'; department : 'LM52'; sex : M; jobClass : Factory);
    i : integer;

(* Main procedure start *)
begin
 (* this seems to be the only way to get a pointer to pass to add() *)
    employeesPtr := getmem(100);
    employeesPtr^ := employees;
	
    tempEmployeePtr := getmem(100);

	(* Attempt to test the binary search tree *)
    for i := 6 to 10 do
    begin
        writeln();
		write('trying to add ');
        writeln(i);
        tempEmployee.number := i;
        tempEmployeePtr^ := tempEmployee;
        employeesPtr := add(tempEmployeePtr, employeesPtr);
    end;    
	
	for i := 1 to 5 do
    begin
		writeln();
		write('trying to add ');
        writeln(i);
        tempEmployee.number := i;
        tempEmployeePtr^ := tempEmployee;
        employeesPtr := add(tempEmployeePtr, employeesPtr);
    end;

    (* Should give employees 1 2 3 4 5 6 7 8 9 10 if tree works correctly *)
	writeln('attempting to print tree');
    printTree(employeesPtr);
end.
