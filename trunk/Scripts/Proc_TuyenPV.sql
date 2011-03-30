use SupportiveLearning
go

create proc Sel_AssignmentsByBacthId
@BatchId int
as
begin
	select * from Assignment where BatchId = @BatchId
end

go
/* Create Procedure Sel_StaffById */

CREATE PROCEDURE Sel_StaffById
@StaffId		int 
AS BEGIN 
	 SELECT Staff.StaffId, Account.* 
	 FROM Staff inner join Account on Staff.AccountId = Account.AccountId
	 WHERE StaffId=@StaffId 
  END 

 GO 

/* Create Procedure Sel_RolesById */

CREATE PROCEDURE Sel_RolesById
@RoleId		int 
AS BEGIN 
	 SELECT RoleId,RoleName,Description 
	 FROM Roles
	 WHERE RoleId=@RoleId 
  END 

 GO
CREATE PROCEDURE Sel_SubjectById
@SubjectId		int 
AS BEGIN 
	 SELECT SubjectId,SubjectName 
	 FROM Subject
	 WHERE SubjectId=@SubjectId 
  END 
GO
CREATE PROCEDURE Sel_BatchById
@BatchId int
AS BEGIN 
	 SELECT * 
	 FROM Batch
	 WHERE BatchId=@BatchId 
  END 
GO

CREATE PROCEDURE Sel_Feedbacks
AS BEGIN 
	 SELECT FeedBackId,StudentId,FeedBackTitle,FeedBackContent,DateCreation 
	 FROM FeedBack
  END
go

CREATE PROCEDURE Sel_AccountByUserNameAndPass
@UserName nvarchar(100),
@Password nvarchar(100)
AS BEGIN
	SELECT * FROM Account
	WHERE UserName = @UserName AND Password = @Password
END
go
 CREATE PROCEDURE Ins_News
@Tittle	nvarchar(255) ,
@Picture	nvarchar(255) ,
@SubContent	nvarchar(500) ,
@NewsContent	ntext ,
@DateCreation	datetime
 
AS BEGIN 
     INSERT INTO News(Tittle,Picture,SubContent,NewsContent,DateCreation  ) 
     VALUES (@Tittle,@Picture,@SubContent,@NewsContent,@DateCreation  ) 
	select SCOPE_IDENTITY()
  END 

 GO 

CREATE PROCEDURE Upd_News 
@Tittle	nvarchar(255) ,
@Picture	nvarchar(255) ,
@SubContent	nvarchar(500) ,
@NewsContent	ntext ,
@DateCreation	datetime  ,
@NewsId	int
AS BEGIN 
     UPDATE News SET 
	Tittle 	 = @Tittle,
	Picture 	 = @Picture,
	SubContent 	 = @SubContent,
	NewsContent 	 = @NewsContent,
	DateCreation 	 = @DateCreation 
    WHERE NewsId=@NewsId 
  END 
GO
CREATE PROCEDURE Del_News 
@NewsId		int 
AS BEGIN 
     DELETE FROM News
     WHERE NewsId=@NewsId 
  END 

 GO 

CREATE PROCEDURE Sel_AllNews
AS BEGIN 
	 SELECT NewsId,Tittle,Picture,SubContent,NewsContent,DateCreation 
	 FROM News ORDER BY NewsId desc
  END

	
go
CREATE PROCEDURE Sel_NewsById
@NewsId		int 
AS BEGIN 
	 SELECT NewsId,Tittle,Picture,SubContent,NewsContent,DateCreation 
	 FROM News
	 WHERE NewsId=@NewsId 
  END 
go
CREATE PROCEDURE Sel_TopNews
AS BEGIN 
	 SELECT TOP(3) NewsId,Tittle,Picture,SubContent,NewsContent,DateCreation 
	 FROM News ORDER BY NewsId desc
		 
  END 
go

CREATE PROCEDURE Sel_StudentWorkByRollNumber
@RollNumber nvarchar(50)
AS BEGIN
SELECT     Student.StudentId, Student.RollNumber, Student.AccountId, Student.BatchId, StudentWork.StudentWorkId, StudentWork.AssignmentId, StudentWork.FileUpload, 
                      StudentWork.Mark, StudentWork.DateUpload
FROM         Student INNER JOIN
                      StudentWork ON Student.StudentId = StudentWork.StudentId
WHERE Student.RollNumber = @RollNumber
END

GO


CREATE PROCEDURE Sel_AssignmentsById
@AssignmentId		int 
AS BEGIN 
	 SELECT *
	 FROM Assignment
	 WHERE AssignmentId=@AssignmentId 
  END 

GO
CREATE PROCEDURE Sel_StudentById 
@StudentId		int
AS BEGIN 
SELECT     Account.AccountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Account.FullName, Account.BirthDay, Account.Gender, 
                      Account.Phone, Account.Email, Account.Address, Student.StudentId, Student.RollNumber, Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId
	 WHERE StudentId=@StudentId 
  END
GO
CREATE PROCEDURE Sel_CourseById
@CourseId int
AS BEGIN 
SELECT * FROM Course
Where CourseId = @CourseId
END
GO

CREATE PROCEDURE Sel_SemesterById
@SemesterId int
AS BEGIN 
SELECT * FROM Semester
Where SemesterId = @SemesterId
END
GO
CREATE PROCEDURE Sel_AccountIsOnline
AS BEGIN
SELECT * FROM Account
WHERE IsOnline = 1 order by LastLogin desc
END
GO
CREATE PROCEDURE SetAccountOnline
@AccountId int,
@IsOnline BIT
AS BEGIN
UPDATE Account SET 
	IsOnline = @IsOnline,
	LastLogin = GETDATE()
WHERE AccountId = @AccountId
END
GO

CREATE PROCEDURE Sel_StudentWorks
AS BEGIN
SELECT * FROM StudentWork
END
GO
CREATE PROCEDURE Sel_StudentWorkByStudentId 
@StudentId int
AS BEGIN
SELECT * FROM StudentWork
 WHERE StudentId = @StudentId
END

GO

CREATE PROCEDURE Sel_StudentWorkById 
@StudentWorkId int
AS BEGIN
SELECT * FROM StudentWork
 WHERE StudentWorkId = @StudentWorkId
END
GO

CREATE PROCEDURE Sel_Students
AS BEGIN 
SELECT     Account.AccountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Account.FullName, Account.BirthDay, Account.Gender, 
                      Account.Phone, Account.Email, Account.Address, Student.StudentId, Student.RollNumber, Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId
  END

GO

CREATE PROCEDURE Sel_StudentsByBatchId
@BatchId int
AS BEGIN 
SELECT     Account.AccountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Account.FullName, Account.BirthDay, Account.Gender, 
                      Account.Phone, Account.Email, Account.Address, Student.StudentId, Student.RollNumber, Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId
WHERE Student.BatchId = @BatchId
  END





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
go


CREATE PROCEDURE Ins_StudentWork
@StudentId int,
@AssignmentId int ,
@FileUpload	nvarchar(255)
AS BEGIN 
     INSERT INTO StudentWork(StudentId,AssignmentId,FileUpload) 
     VALUES (@StudentId,@AssignmentId,@FileUpload) 
  END 

go

CREATE  PROCEDURE Sel_BatchsOfStaff
@StaffId int
AS BEGIN
SELECT     Batch.BatchName, Batch.StartDate, Staff.AccountId, StaffAndBatch.StaffAndBatchId, StaffAndBatch.StaffId, StaffAndBatch.BatchId
FROM         Batch INNER JOIN
                      StaffAndBatch ON Batch.BatchId = StaffAndBatch.BatchId INNER JOIN
                      Staff ON StaffAndBatch.StaffId = Staff.StaffId

where StaffAndBatch.StaffId = @StaffId
END
go

CREATE  PROCEDURE Sel_StaffByAccountId
@AccountId	int 
AS BEGIN 
	 SELECT Staff.StaffId, Account.* 
	 FROM Staff inner join Account on Staff.AccountId = Account.AccountId
	 WHERE Account.AccountId=@AccountId 
  END 
go

CREATE PROCEDURE Sel_StudentByAssignmentId
@AssignmentId int
AS BEGIN
SELECT     Student.StudentId, Student.RollNumber, Student.AccountId, Student.BatchId, StudentWork.StudentWorkId, StudentWork.AssignmentId, StudentWork.FileUpload, 
                      StudentWork.Mark, StudentWork.DateUpload
FROM         Student INNER JOIN
                      StudentWork ON Student.StudentId = StudentWork.StudentId
WHERE StudentWork.AssignmentId @AssignmentId
END


---------------------------------------Proc Batch
go
CREATE PROCEDURE Sel_StudentForAddToBatch
AS BEGIN
SELECT     Account.AccountId, Account.RoleId, Account.FullName, Account.BirthDay, Account.Gender, Account.Phone, Account.Email, Account.Address, Student.StudentId, 
                      Student.RollNumber,  Account.DateCreation,Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId

where Student.BatchId IS NULL
order by Account.DateCreation desc
end
go


CREATE PROCEDURE Sel_AllBatch
AS BEGIN
select * from Batch
END

go
CREATE PROCEDURE Sel_AllStudentHaveBatch
AS BEGIN
SELECT     Account.AccountId, Account.RoleId, Account.FullName, Account.BirthDay, Account.Gender, Account.Phone, Account.Email, Account.Address, Student.StudentId, 
                      Student.RollNumber,  Account.DateCreation,Student.BatchId
FROM         Account INNER JOIN
                      Student ON Account.AccountId = Student.AccountId

where Student.BatchId is not NULL
order by Account.DateCreation desc
end

go

CREATE PROCEDURE Upd_BatchToStudent
@StudentId int,
@BatchId int
AS BEGIN 
     UPDATE Student SET 
	BatchId 	 = @BatchId
    WHERE StudentId=@StudentId 
  END 
GO

---------------------------------------Proc Batch





select * from StudentWork

select * from Account

select * from Roles