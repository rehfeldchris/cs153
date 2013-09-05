{
The purpose of this code is to parse the lines in a txt file,
and use the data to create Employee records. Once the records are created,
they are placed in a binary tree to make sorting by employee number possible.


I copied some of the type declarations from Michaels file and used them here, but the Employee
Record type had to be modified to use a LongInt for the number field.

The code works with the exception that I'm having trouble parsing a string into an integer,
so all employee numbers are "0" for now. I'll fix that soon, but in the meantime other
people should be able to use my code if needed.


My PrintEmployee() proc does print records to output, but I only did this for debugging,
not to meet to assignment requirements.

@author chris rehfeld
@author brian huber
}

program EmployeeRecordParserReader;

uses
  SysUtils,
  TypInfo;

type
    SexType = (M, F);
    JobClassType = (Factory, Office, Supervisor, VP, President);
    Employee = Record
                 number : LongInt;
                 lastName : string[8];
                 initials : string[2];
                 birthday : string[8];
                 department : string[4];
                 sex : SexType;
                 jobClass : JobClassType;
            end;
    EmployeeArray = array of Employee;

    WorkerPointer = ^WorkerType;
    WorkerType = record
       Info : Employee;
       Left : WorkerPointer;
       Right : WorkerPointer;
    end;


Var UserFile : Text;
    FileName : String;
    Employees : EmployeeArray;
    i : Integer;
    TreeRoot : WorkerPointer;

{parses a string, using the data to populate an Employee record}
function StrToEmployee(var line : String) : Employee;

{I don't feel great about returning this local Employee record variable. I worry its mem is allocated on the stack,
and might get deallocated after the func returns, but it seems to work lol.}
var
empl : Employee;
JobTypeStr : String;
err : LongInt;
begin
	Val(copy(line, 1, 7), empl.number, err);
	empl.lastName   := copy(line, 9, 8);
	empl.initials   := copy(line, 17, 2);
	empl.birthday   := copy(line, 20, 8);
	empl.department := copy(line, 29, 4);

	if copy(line, 34, 1) = 'M' then
		empl.sex := SexType.M
	else
		empl.sex := SexType.F;

	JobTypeStr := copy(line, 36, 1);
	if JobTypeStr = 'F' then
		empl.jobClass 	:= JobClassType.Factory;
	if JobTypeStr = 'O' then
		empl.jobClass 	:= JobClassType.Office;
	if JobTypeStr = 'S' then
		empl.jobClass 	:= JobClassType.Supervisor;
	if JobTypeStr = 'V' then
		empl.jobClass 	:= JobClassType.VP;
	if JobTypeStr = 'P' then
		empl.jobClass 	:= JobClassType.President;

	StrToEmployee := empl;
end;

{this reads a file line by line, parsing the data into Employee records. It returns a zero-based array of Employee}
function GetEmployeeRecordsFromFile(var FileName : String) : EmployeeArray;
var i 	 : Integer = -1;{counter used for array index, and setting the size of array}
    line : String;{temporarily holds a line of our txt file}
begin
	Assign(UserFile, FileName);
	Reset(UserFile);
	Repeat
		i := i + 1;

		{SetLength is used to grow the array size dynamically}
		SetLength(GetEmployeeRecordsFromFile, i + 1);

		{read the next line from the file into our string var}
		Readln(UserFile, line);

		{parse the line into an employee record, and then put it into our array}
		GetEmployeeRecordsFromFile[i] := StrToEmployee(line);

	Until Eof(UserFile);
	Close(UserFile);
end;

{prints the contents of an Employee}
procedure PrintEmployee(empl : Employee);
begin
	WriteLn(Format(
		'%d %s %s %s %s %s %s',
		[
			empl.number,
			empl.lastName,
			empl.initials,
			empl.birthday,
			empl.department,
			GetEnumName(TypeInfo(SexType), ord(empl.sex)),
			GetEnumName(TypeInfo(JobClassType), ord(empl.jobClass))
		]
	));
end;

{inserts employee record into binary tree}
procedure Insert (Var CurrentWorker : Employee; Var TreeNode : WorkerPointer);
begin
   if (TreeNode = nil) then
     begin
       new (TreeNode);
       TreeNode^.Info := CurrentWorker;
       TreeNode^.Left := nil;
       TreeNode^.Right := nil;
     end
   else
     if (CurrentWorker.number < TreeNode^.Info.number) then
       Insert (CurrentWorker, TreeNode^.Left)
     else
       Insert (CurrentWorker, TreeNode^.Right);
end;

{traverses binary tree, printing out employee records along the way}
procedure Traverse (TreeNode : WorkerPointer);
begin
  if (TreeNode <> nil) then
    begin
      Traverse (TreeNode^.Left);
      PrintEmployee (TreeNode^.Info);
      Traverse (TreeNode^.Right);
    end;
end;

{deallocates the memory used by the binary tree}
procedure CleanUp (var TreeNode : WorkerPointer);
begin
  if (TreeNode <> nil) then
    begin
      CleanUp (TreeNode^.Left);
      CleanUp (TreeNode^.Right);
      dispose (TreeNode);
    end;
end;

{begin main. this is only to demo usage.}
Begin
        TreeRoot := nil;
	FileName  := 'employees.in';
	Employees := GetEmployeeRecordsFromFile(FileName);

	for i := 0 to High(Employees) do
          Insert (Employees[i], TreeRoot);

        Traverse (TreeRoot);
        CleanUp (TreeRoot);

	{readln is a hack to keep the console window from closing automatically}
	readln;
End.
