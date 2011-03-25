
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

