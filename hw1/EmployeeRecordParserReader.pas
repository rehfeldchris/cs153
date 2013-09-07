{
@HomeworkAssignment #1
@date 2013-09-06
@group compiler compilers
@author chris rehfeld
@author brian huber
@author michael riha
}

program EmployeeRecordParserReader;

uses
  SysUtils,
  fgl,
  TypInfo;

type
    SexType = (M, F);
    JobClassType = (Factory, Office, Supervisor, VP, President);
    Employee = Record
                 number : LongInt;
                 lastName : string[8];
                 initials : string[2];
                 birthday : string[2];
                 birthyear : string[4];
                 birthmonth : string[2];
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
    
    DeptNameArray = array of String;
    DeptCntArray = array of Integer;



Var UserFile : Text;
    OutFile : Text;
    FileName : String;
    Employees : EmployeeArray;
    i : Integer;
    TreeRoot : WorkerPointer;
    DeptNames : DeptNameArray;
    DeptCnts : DeptCntArray;
    DeptNamesLength : Integer = 0;

{parses a string, using the data to populate an Employee record}
function StrToEmployee(var line : String) : Employee;

var
empl : Employee;
JobTypeStr : String;
err : LongInt;
begin
    Val(copy(line, 1, 7), empl.number, err);
    empl.lastName   := copy(line, 9, 8);
    empl.initials   := copy(line, 17, 2);
        empl.birthyear := copy(line, 20, 4);
        empl.birthmonth := copy(line, 24, 2);
        empl.birthday := copy(line, 26, 2);
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

procedure WriteHeader();
begin
  writeln(OutFile, Format('%s   %s   %s   %18s      %s   %s   %s',
                        [
                          'Employee Number',
                          'Last Name',
                          'Initials',
                          'Birthday',
                          'Department',
                          'Gender',
                          'Job Classification'
                        ]));
  write(OutFile, '----------------------------------------------------------');
  writeln(OutFile, '-----------------------------------------------');
end;

function GetMonth (MonthString : String) : String;
begin
    case MonthString of
      '01' : GetMonth := 'January';
      '02' : GetMonth := 'February';
      '03' : GetMonth := 'March';
      '04' : GetMonth := 'April';
      '05' : GetMonth := 'May';
      '06' : GetMonth := 'June';
      '07' : GetMonth := 'July';
      '08' : GetMonth := 'August';
      '09' : GetMonth := 'September';
      '10' : GetMonth := 'October';
      '11' : GetMonth := 'November';
      '12' : GetMonth := 'December'
    end;
end;

function findIndex (key : String) : Integer;
var i : Integer;
begin
    findIndex := -1;
    for i := 0 to High(DeptNames) do
    begin
        if DeptNames[i] = key then
            findIndex := i;
    end;
end;

procedure increase(key : String);
var index : Integer;
begin

    index := findIndex(key);
    if index = -1 then
    begin
        index := DeptNamesLength;
        DeptNamesLength := DeptNamesLength + 1;
        SetLength(DeptNames, DeptNamesLength);
        SetLength(DeptCnts, DeptNamesLength);
        DeptNames[index] := key;
        DeptCnts[index] := 0;
    end;
    
    DeptCnts[index] := DeptCnts[index] + 1;
end;

procedure PrintSummary (Employees : EmployeeArray);
var WorkerTypes : set of JobClassType = [JobClassType.Factory, JobClassType.Office];
    ManagerTypes : set of JobClassType = [JobClassType.Supervisor, JobClassType.VP, JobClassType.President];
    i, maleCnt, femaleCnt, workerCnt, managerCnt : Integer;
begin
    i := 0;
    maleCnt := 0;
    femaleCnt := 0;
    workerCnt := 0;
    managerCnt := 0;

    for i := 0 to High(Employees) do
    begin
        {@@@@LOOK@@@@ set operations here!!}
        if Employees[i].jobClass in WorkerTypes then 
            workerCnt := workerCnt + 1;
            
        if Employees[i].jobClass in ManagerTypes then 
            managerCnt := managerCnt + 1;
            
        if Employees[i].sex = SexType.M then 
            maleCnt := maleCnt + 1;
            
        if Employees[i].sex = SexType.F then 
            femaleCnt := femaleCnt + 1;
            
        increase(Employees[i].department);
    end;
    WriteLn(OutFile, '--Category Counts--');
    WriteLn(OutFile, Format('    Male: %d', [maleCnt]));
    WriteLn(OutFile, Format('  Female: %d', [femaleCnt]));
    WriteLn(OutFile, Format(' Workers: %d', [workerCnt]));
    WriteLn(OutFile, Format('Managers: %d', [managerCnt]));
    WriteLn(OutFile, '--Department Head Count--');
    for i := 0 to High(DeptNames) do
    begin
        WriteLn(OutFile, Format('%8s: %d', [DeptNames[i], DeptCnts[i]]));
    end;
end;

{writes the contents of an Employee to a file}
procedure WriteEmployee(empl : Employee);
var
    MonthTemp : string[9];
begin
        MonthTemp := GetMonth(empl.birthmonth);
    WriteLn(OutFile, Format(
        '%d%19s%6s%18s %2s%s %4s%10s%10s%26s',
        [
            empl.number,
            empl.lastName,
            empl.initials,
            MonthTemp,
            empl.birthday,
            ',',
            empl.birthyear,
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

{traverses binary tree, writing out employee records along the way}
procedure Traverse (TreeNode : WorkerPointer);
begin
  if (TreeNode <> nil) then
    begin
      Traverse (TreeNode^.Left);
      WriteEmployee (TreeNode^.Info);
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
    WriteLn('all output will be written to output.txt');
    TreeRoot := nil;
    FileName  := 'employees.in';
    Assign (OutFile, 'output.txt');
    Rewrite (OutFile);
    Employees := GetEmployeeRecordsFromFile(FileName);

    for i := 0 to High(Employees) do
          Insert (Employees[i], TreeRoot);

    WriteHeader();
    Traverse (TreeRoot);
    PrintSummary (Employees);
    Close (OutFile);
    CleanUp (TreeRoot);

    {readln is a hack to keep the console window from closing automatically}
    readln;
End.
