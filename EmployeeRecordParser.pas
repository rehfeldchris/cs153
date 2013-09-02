{
The purpose of this code is to parse the lines in a txt file, 
and use the data to create Employee records.


I copied some of the type declarations from Michaels file and used them here.

The code works with the exception that I'm having trouble parsing a string into an integer, 
so all employee numbers are "0" for now. I'll fix that soon, but in the meantime other 
people should be able to use my code if needed.


My PrintEmployee() proc does print records to output, but I only did this for debugging, 
not to meet to assignment requirements.

@author chris rehfeld
}

program EmployeeRecordParser;

uses
  SysUtils,
  TypInfo;

type
    SexType = (M, F);
    JobClassType = (Factory, Office, Supervisor, VP, President);
    Employee = Record
                 number : integer;
                 lastName : string[8];
                 initials : string[2];
                 birthday : string[8];
                 department : string[4];
                 sex : SexType;
                 jobClass : JobClassType;
            end;
	EmployeeArray = array of Employee;


Var UserFile : Text;
    FileName : String;
	Employees : EmployeeArray;
	i : Integer;

{parses a string, using the data to populate an Employee record}
function StrToEmployee(var line : String) : Employee;

{I don't feel great about returning this local Employee record variable. I worry its mem is allocated on the stack, 
and might get deallocated after the func returns, but it seems to work lol.}
var
empl : Employee;
JobTypeStr : String;

begin
	//empl.number		:= StrToInt(copy(line, 1, 8));
	empl.number		:= 0;
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
	
{begin main. this is only to demo usage.}
Begin
	FileName  := 'employees.in';
	Employees := GetEmployeeRecordsFromFile(FileName);
	
	for i := 0 to High(Employees) do
		PrintEmployee(Employees[i]);
		
	{readln is a hack to keep the console window from closing automatically}
	readln;
End.