
CREATE PROCEDURE Sel_StudentByAccountId
@AccountId		int
AS BEGIN 
SELECT     Account.AccountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Account.FullName, Account.BirthDay, Account.Gender, 
                      Account.Phone, Account.Email, Account.Address, Student.StudentId, Student.RollNumber, Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId
	 WHERE Student.AccountId=@AccountId 
  END
GO



CREATE PROCEDURE Sel_AccountById
@AccountId int
AS BEGIN
	SELECT * FROM Account
	WHERE AccountId = @AccountId
END



CREATE PROCEDURE Ins_StudentWork
@StudentId int,
@AssignmentId int ,
@FileUpload	nvarchar(255)
AS BEGIN 
     INSERT INTO StudentWork(StudentId,AssignmentId,FileUpload) 
     VALUES (@StudentId,@AssignmentId,@FileUpload) 
  END 




CREATE  PROCEDURE Sel_BatchsOfStaff
@StaffId int
AS BEGIN
SELECT     Staff.StaffId, Batch.BatchId, Batch.CourseId, Batch.SemesterId, Batch.BatchName, Batch.StartDate
FROM         Batch CROSS JOIN
                      Staff
where Staff.StaffId = @StaffId
END


CREATE  PROCEDURE Sel_StaffByAccountId
@AccountId	int 
AS BEGIN 
	 SELECT Staff.StaffId, Account.* 
	 FROM Staff inner join Account on Staff.AccountId = Account.AccountId
	 WHERE Account.AccountId=@AccountId 
  END 