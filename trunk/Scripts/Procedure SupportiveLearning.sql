use SupportiveLearning

 GO 
/* Create Procedure Sel_AllRoles*/

CREATE PROCEDURE Sel_AllRoles
AS BEGIN 
	 SELECT RoleId,RoleName,Description 
	 FROM Roles
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

/* Create Procedure Udp_RolesById */

CREATE PROCEDURE Udp_RolesById 
@RoleId	int ,
@RoleName	nvarchar(100) ,
@Description	nvarchar(100)  
AS BEGIN 
     UPDATE Roles SET 
	RoleName 	 = @RoleName,
	Description 	 = @Description 
    WHERE RoleId=@RoleId 
  END 

 GO 

/* Create Procedure Ins_Roles */

CREATE PROCEDURE Ins_Roles
@RoleName	nvarchar(100) ,
@Description	nvarchar(100)  
AS BEGIN 
     INSERT INTO Roles(RoleName,Description  ) 
     VALUES (@RoleName,@Description  ) 
  END 

 GO 

/* Create Procedure Del_RolesById */

CREATE PROCEDURE Del_RolesById 
@RoleId		int 
AS BEGIN 
     DELETE FROM Roles
     WHERE RoleId=@RoleId 
  END 

 GO 

/* Create Procedure Sel_AllAccount*/

CREATE PROCEDURE Sel_AllAccount
AS BEGIN 
	 SELECT AcountId,RoleId,UserName,PassWord,DateCreate 
	 FROM Account
  END 

 GO 

/* Create Procedure Sel_AccountById */

CREATE PROCEDURE Sel_AccountById
@AcountId		int 
AS BEGIN 
	 SELECT AcountId,RoleId,UserName,PassWord,DateCreate 
	 FROM Account
	 WHERE AcountId=@AcountId 
  END 

 GO 

/* Create Procedure Udp_AccountById */

CREATE PROCEDURE Udp_AccountById 
@AcountId	int ,
@RoleId	int ,
@UserName	nvarchar(100) ,
@PassWord	nvarchar(100) ,
@DateCreate	datetime  
AS BEGIN 
     UPDATE Account SET 
	RoleId 	 = @RoleId,
	UserName 	 = @UserName,
	PassWord 	 = @PassWord,
	DateCreate 	 = @DateCreate 
    WHERE AcountId=@AcountId 
  END 

 GO 

/* Create Procedure Ins_Account */

CREATE PROCEDURE Ins_Account
@RoleId	int ,
@UserName	nvarchar(100) ,
@PassWord	nvarchar(100) ,
@DateCreate	datetime  
AS BEGIN 
     INSERT INTO Account(RoleId,UserName,PassWord,DateCreate  ) 
     VALUES (@RoleId,@UserName,@PassWord,@DateCreate  ) 
  END 

 GO 

/* Create Procedure Del_AccountById */

CREATE PROCEDURE Del_AccountById 
@AcountId		int 
AS BEGIN 
     DELETE FROM Account
     WHERE AcountId=@AcountId 
  END 

 GO 

/* Create Procedure Sel_AllCourse*/

CREATE PROCEDURE Sel_AllCourse
AS BEGIN 
	 SELECT CourseId,CourseName,DateStart,DateEnd,Batch 
	 FROM Course
  END 

 GO 

/* Create Procedure Sel_CourseById */

CREATE PROCEDURE Sel_CourseById
@CourseId		int 
AS BEGIN 
	 SELECT CourseId,CourseName,DateStart,DateEnd,Batch 
	 FROM Course
	 WHERE CourseId=@CourseId 
  END 

 GO 

/* Create Procedure Udp_CourseById */

CREATE PROCEDURE Udp_CourseById 
@CourseId	int ,
@CourseName	nvarchar(100) ,
@DateStart	datetime ,
@DateEnd	datetime ,
@Batch	nvarchar(100)  
AS BEGIN 
     UPDATE Course SET 
	CourseName 	 = @CourseName,
	DateStart 	 = @DateStart,
	DateEnd 	 = @DateEnd,
	Batch 	 = @Batch 
    WHERE CourseId=@CourseId 
  END 

 GO 

/* Create Procedure Ins_Course */

CREATE PROCEDURE Ins_Course
@CourseName	nvarchar(100) ,
@DateStart	datetime ,
@DateEnd	datetime ,
@Batch	nvarchar(100)  
AS BEGIN 
     INSERT INTO Course(CourseName,DateStart,DateEnd,Batch  ) 
     VALUES (@CourseName,@DateStart,@DateEnd,@Batch  ) 
  END 

 GO 

/* Create Procedure Del_CourseById */

CREATE PROCEDURE Del_CourseById 
@CourseId		int 
AS BEGIN 
     DELETE FROM Course
     WHERE CourseId=@CourseId 
  END 

 GO 

/* Create Procedure Sel_AllSemester*/

CREATE PROCEDURE Sel_AllSemester
AS BEGIN 
	 SELECT SemesterId,SemesterName,SemesterTime 
	 FROM Semester
  END 

 GO 

/* Create Procedure Sel_SemesterById */

CREATE PROCEDURE Sel_SemesterById
@SemesterId		int 
AS BEGIN 
	 SELECT SemesterId,SemesterName,SemesterTime 
	 FROM Semester
	 WHERE SemesterId=@SemesterId 
  END 

 GO 

/* Create Procedure Udp_SemesterById */

CREATE PROCEDURE Udp_SemesterById 
@SemesterId	int ,
@SemesterName	nvarchar(100) ,
@SemesterTime	int  
AS BEGIN 
     UPDATE Semester SET 
	SemesterName 	 = @SemesterName,
	SemesterTime 	 = @SemesterTime 
    WHERE SemesterId=@SemesterId 
  END 

 GO 

/* Create Procedure Ins_Semester */

CREATE PROCEDURE Ins_Semester
@SemesterName	nvarchar(100) ,
@SemesterTime	int  
AS BEGIN 
     INSERT INTO Semester(SemesterName,SemesterTime  ) 
     VALUES (@SemesterName,@SemesterTime  ) 
  END 

 GO 

/* Create Procedure Del_SemesterById */

CREATE PROCEDURE Del_SemesterById 
@SemesterId		int 
AS BEGIN 
     DELETE FROM Semester
     WHERE SemesterId=@SemesterId 
  END 

 GO 

/* Create Procedure Sel_AllClazz*/

CREATE PROCEDURE Sel_AllClazz
AS BEGIN 
	 SELECT ClazzId,CourseId,SemesterId,ClazzName,StartDate 
	 FROM Clazz
  END 

 GO 

/* Create Procedure Sel_ClazzById */

CREATE PROCEDURE Sel_ClazzById
@ClazzId		int 
AS BEGIN 
	 SELECT ClazzId,CourseId,SemesterId,ClazzName,StartDate 
	 FROM Clazz
	 WHERE ClazzId=@ClazzId 
  END 

 GO 

/* Create Procedure Udp_ClazzById */

CREATE PROCEDURE Udp_ClazzById 
@ClazzId	int ,
@CourseId	int ,
@SemesterId	int ,
@ClazzName	nvarchar(100) ,
@StartDate	datetime  
AS BEGIN 
     UPDATE Clazz SET 
	CourseId 	 = @CourseId,
	SemesterId 	 = @SemesterId,
	ClazzName 	 = @ClazzName,
	StartDate 	 = @StartDate 
    WHERE ClazzId=@ClazzId 
  END 

 GO 

/* Create Procedure Ins_Clazz */

CREATE PROCEDURE Ins_Clazz
@CourseId	int ,
@SemesterId	int ,
@ClazzName	nvarchar(100) ,
@StartDate	datetime  
AS BEGIN 
     INSERT INTO Clazz(CourseId,SemesterId,ClazzName,StartDate  ) 
     VALUES (@CourseId,@SemesterId,@ClazzName,@StartDate  ) 
  END 

 GO 

/* Create Procedure Del_ClazzById */

CREATE PROCEDURE Del_ClazzById 
@ClazzId		int 
AS BEGIN 
     DELETE FROM Clazz
     WHERE ClazzId=@ClazzId 
  END 

 GO 

/* Create Procedure Sel_AllStaff*/

CREATE PROCEDURE Sel_AllStaff
AS BEGIN 
	 SELECT StaffId,AcountId,StaffName 
	 FROM Staff
  END 

 GO 

/* Create Procedure Sel_StaffById */

CREATE PROCEDURE Sel_StaffById
@StaffId		int 
AS BEGIN 
	 SELECT StaffId,AcountId,StaffName 
	 FROM Staff
	 WHERE StaffId=@StaffId 
  END 

 GO 

/* Create Procedure Udp_StaffById */

CREATE PROCEDURE Udp_StaffById 
@StaffId	int ,
@AcountId	int ,
@StaffName	nvarchar(100)  
AS BEGIN 
     UPDATE Staff SET 
	AcountId 	 = @AcountId,
	StaffName 	 = @StaffName 
    WHERE StaffId=@StaffId 
  END 

 GO 

/* Create Procedure Ins_Staff */

CREATE PROCEDURE Ins_Staff
@AcountId	int ,
@StaffName	nvarchar(100)  
AS BEGIN 
     INSERT INTO Staff(AcountId,StaffName  ) 
     VALUES (@AcountId,@StaffName  ) 
  END 

 GO 

/* Create Procedure Del_StaffById */

CREATE PROCEDURE Del_StaffById 
@StaffId		int 
AS BEGIN 
     DELETE FROM Staff
     WHERE StaffId=@StaffId 
  END 

 GO 

/* Create Procedure Sel_AllStaffAndClazz*/

CREATE PROCEDURE Sel_AllStaffAndClazz
AS BEGIN 
	 SELECT StaffAndClazzId,StaffId,ClazzId 
	 FROM StaffAndClazz
  END 

 GO 

/* Create Procedure Sel_StaffAndClazzById */

CREATE PROCEDURE Sel_StaffAndClazzById
@StaffAndClazzId		int 
AS BEGIN 
	 SELECT StaffAndClazzId,StaffId,ClazzId 
	 FROM StaffAndClazz
	 WHERE StaffAndClazzId=@StaffAndClazzId 
  END 

 GO 

/* Create Procedure Udp_StaffAndClazzById */

CREATE PROCEDURE Udp_StaffAndClazzById 
@StaffAndClazzId	int ,
@StaffId	int ,
@ClazzId	int  
AS BEGIN 
     UPDATE StaffAndClazz SET 
	StaffId 	 = @StaffId,
	ClazzId 	 = @ClazzId 
    WHERE StaffAndClazzId=@StaffAndClazzId 
  END 

 GO 

/* Create Procedure Ins_StaffAndClazz */

CREATE PROCEDURE Ins_StaffAndClazz
@StaffId	int ,
@ClazzId	int  
AS BEGIN 
     INSERT INTO StaffAndClazz(StaffId,ClazzId  ) 
     VALUES (@StaffId,@ClazzId  ) 
  END 

 GO 

/* Create Procedure Del_StaffAndClazzById */

CREATE PROCEDURE Del_StaffAndClazzById 
@StaffAndClazzId		int 
AS BEGIN 
     DELETE FROM StaffAndClazz
     WHERE StaffAndClazzId=@StaffAndClazzId 
  END 

 GO 

/* Create Procedure Sel_AllStudent*/

CREATE PROCEDURE Sel_AllStudent
AS BEGIN 
	 SELECT StudentId,AcountId,ClazzId,FullName,BirthDay,Gender,Phone,Email,Address 
	 FROM Student
  END 

 GO  

/* Create Procedure Udp_StudentById */

CREATE PROCEDURE Udp_StudentById 
@StudentId	int ,
@AcountId	int ,
@ClazzId	int ,
@FullName	nvarchar(200) ,
@BirthDay	datetime ,
@Gender	bit ,
@Phone	nvarchar(100) ,
@Email	nvarchar(100) ,
@Address	nvarchar(255)  
AS BEGIN 
     UPDATE Student SET 
	AcountId 	 = @AcountId,
	ClazzId 	 = @ClazzId,
	FullName 	 = @FullName,
	BirthDay 	 = @BirthDay,
	Gender 	 = @Gender,
	Phone 	 = @Phone,
	Email 	 = @Email,
	Address 	 = @Address 
    WHERE StudentId=@StudentId 
  END 

 GO 

/* Create Procedure Ins_Student */

CREATE PROCEDURE Ins_Student
@AcountId	int ,
@ClazzId	int ,
@FullName	nvarchar(200) ,
@BirthDay	datetime ,
@Gender	bit ,
@Phone	nvarchar(100) ,
@Email	nvarchar(100) ,
@Address	nvarchar(255)  
AS BEGIN 
     INSERT INTO Student(AcountId,ClazzId,FullName,BirthDay,Gender,Phone,Email,Address  ) 
     VALUES (@AcountId,@ClazzId,@FullName,@BirthDay,@Gender,@Phone,@Email,@Address  ) 
  END 

 GO 

/* Create Procedure Del_StudentById */

CREATE PROCEDURE Del_StudentById 
@StudentId		int 
AS BEGIN 
     DELETE FROM Student
     WHERE StudentId=@StudentId 
  END 

 GO 

/* Create Procedure Sel_AllSubject*/

CREATE PROCEDURE Sel_AllSubject
AS BEGIN 
	 SELECT SubjectId,SubjectName,StartData,EndDate 
	 FROM Subject
  END 

 GO 

/* Create Procedure Sel_SubjectById */

CREATE PROCEDURE Sel_SubjectById
@SubjectId		int 
AS BEGIN 
	 SELECT SubjectId,SubjectName,StartData,EndDate 
	 FROM Subject
	 WHERE SubjectId=@SubjectId 
  END 

 GO 

/* Create Procedure Udp_SubjectById */

CREATE PROCEDURE Udp_SubjectById 
@SubjectId	int ,
@SubjectName	nvarchar(100) ,
@StartData	datetime ,
@EndDate	datetime  
AS BEGIN 
     UPDATE Subject SET 
	SubjectName 	 = @SubjectName,
	StartData 	 = @StartData,
	EndDate 	 = @EndDate 
    WHERE SubjectId=@SubjectId 
  END 

 GO 

/* Create Procedure Ins_Subject */

CREATE PROCEDURE Ins_Subject
@SubjectName	nvarchar(100) ,
@StartData	datetime ,
@EndDate	datetime  
AS BEGIN 
     INSERT INTO Subject(SubjectName,StartData,EndDate  ) 
     VALUES (@SubjectName,@StartData,@EndDate  ) 
  END 

 GO 

/* Create Procedure Del_SubjectById */

CREATE PROCEDURE Del_SubjectById 
@SubjectId		int 
AS BEGIN 
     DELETE FROM Subject
     WHERE SubjectId=@SubjectId 
  END 

 GO 

/* Create Procedure Sel_AllAssignment*/

CREATE PROCEDURE Sel_AllAssignment
AS BEGIN 
	 SELECT AssignmentId,SubjectId,StudentId,StaffId,AssignmentName,FileUpload,Mark,DateUpload 
	 FROM Assignment
  END 

 GO 

/* Create Procedure Sel_AssignmentById */

CREATE PROCEDURE Sel_AssignmentById
@AssignmentId		int 
AS BEGIN 
	 SELECT AssignmentId,SubjectId,StudentId,StaffId,AssignmentName,FileUpload,Mark,DateUpload 
	 FROM Assignment
	 WHERE AssignmentId=@AssignmentId 
  END 

 GO 

/* Create Procedure Udp_AssignmentById */

CREATE PROCEDURE Udp_AssignmentById 
@AssignmentId	int ,
@SubjectId	int ,
@StudentId	int ,
@StaffId	int ,
@AssignmentName	nvarchar(100) ,
@FileUpload	nvarchar(255) ,
@Mark	float ,
@DateUpload	datetime  
AS BEGIN 
     UPDATE Assignment SET 
	SubjectId 	 = @SubjectId,
	StudentId 	 = @StudentId,
	StaffId 	 = @StaffId,
	AssignmentName 	 = @AssignmentName,
	FileUpload 	 = @FileUpload,
	Mark 	 = @Mark,
	DateUpload 	 = @DateUpload 
    WHERE AssignmentId=@AssignmentId 
  END 

 GO 

/* Create Procedure Ins_Assignment */

CREATE PROCEDURE Ins_Assignment
@SubjectId	int ,
@StudentId	int ,
@StaffId	int ,
@AssignmentName	nvarchar(100) ,
@FileUpload	nvarchar(255) ,
@Mark	float ,
@DateUpload	datetime  
AS BEGIN 
     INSERT INTO Assignment(SubjectId,StudentId,StaffId,AssignmentName,FileUpload,Mark,DateUpload  ) 
     VALUES (@SubjectId,@StudentId,@StaffId,@AssignmentName,@FileUpload,@Mark,@DateUpload  ) 
  END 

 GO 

/* Create Procedure Del_AssignmentById */

CREATE PROCEDURE Del_AssignmentById 
@AssignmentId		int 
AS BEGIN 
     DELETE FROM Assignment
     WHERE AssignmentId=@AssignmentId 
  END 

 GO 

/* Create Procedure Sel_AllAdmin*/

CREATE PROCEDURE Sel_AllAdmin
AS BEGIN 
	 SELECT AdminId,AcountId 
	 FROM Admin
  END 

 GO 

/* Create Procedure Sel_AdminById */

CREATE PROCEDURE Sel_AdminById
@AdminId		int 
AS BEGIN 
	 SELECT AdminId,AcountId 
	 FROM Admin
	 WHERE AdminId=@AdminId 
  END 

 GO 

/* Create Procedure Udp_AdminById */

CREATE PROCEDURE Udp_AdminById 
@AdminId	int ,
@AcountId	int  
AS BEGIN 
     UPDATE Admin SET 
	AcountId 	 = @AcountId 
    WHERE AdminId=@AdminId 
  END 

 GO 

/* Create Procedure Ins_Admin */

CREATE PROCEDURE Ins_Admin
@AcountId	int  
AS BEGIN 
     INSERT INTO Admin(AcountId  ) 
     VALUES (@AcountId  ) 
  END 

 GO 

/* Create Procedure Del_AdminById */

CREATE PROCEDURE Del_AdminById 
@AdminId		int 
AS BEGIN 
     DELETE FROM Admin
     WHERE AdminId=@AdminId 
  END 

 GO 

/* Create Procedure Sel_AllFAQ*/

CREATE PROCEDURE Sel_AllFAQ
AS BEGIN 
	 SELECT FAQId,Quesion,Answer,DateCreation 
	 FROM FAQ
  END 

 GO 

/* Create Procedure Sel_FAQById */

CREATE PROCEDURE Sel_FAQById
@FAQId		int 
AS BEGIN 
	 SELECT FAQId,Quesion,Answer,DateCreation 
	 FROM FAQ
	 WHERE FAQId=@FAQId 
  END 

 GO 

/* Create Procedure Udp_FAQById */

CREATE PROCEDURE Udp_FAQById 
@FAQId	int ,
@Quesion	ntext ,
@Answer	ntext ,
@DateCreation	datetime  
AS BEGIN 
     UPDATE FAQ SET 
	Quesion 	 = @Quesion,
	Answer 	 = @Answer,
	DateCreation 	 = @DateCreation 
    WHERE FAQId=@FAQId 
  END 

 GO 

/* Create Procedure Ins_FAQ */

CREATE PROCEDURE Ins_FAQ
@Quesion	ntext ,
@Answer	ntext ,
@DateCreation	datetime  
AS BEGIN 
     INSERT INTO FAQ(Quesion,Answer,DateCreation  ) 
     VALUES (@Quesion,@Answer,@DateCreation  ) 
  END 

 GO 

/* Create Procedure Del_FAQById */

CREATE PROCEDURE Del_FAQById 
@FAQId		int 
AS BEGIN 
     DELETE FROM FAQ
     WHERE FAQId=@FAQId 
  END 

 GO 

/* Create Procedure Sel_AllFeedBack*/

CREATE PROCEDURE Sel_AllFeedBack
AS BEGIN 
	 SELECT FeedBackId,AcountId,FeedBackTitle,FeedBackContent,DateCreation 
	 FROM FeedBack
  END 

 GO 

/* Create Procedure Sel_FeedBackById */

CREATE PROCEDURE Sel_FeedBackById
@FeedBackId		int 
AS BEGIN 
	 SELECT FeedBackId,AcountId,FeedBackTitle,FeedBackContent,DateCreation 
	 FROM FeedBack
	 WHERE FeedBackId=@FeedBackId 
  END 

 GO 

/* Create Procedure Udp_FeedBackById */

CREATE PROCEDURE Udp_FeedBackById 
@FeedBackId	int ,
@AcountId	int ,
@FeedBackTitle	nvarchar(100) ,
@FeedBackContent	ntext ,
@DateCreation	datetime  
AS BEGIN 
     UPDATE FeedBack SET 
	AcountId 	 = @AcountId,
	FeedBackTitle 	 = @FeedBackTitle,
	FeedBackContent 	 = @FeedBackContent,
	DateCreation 	 = @DateCreation 
    WHERE FeedBackId=@FeedBackId 
  END 

 GO 

/* Create Procedure Ins_FeedBack */

CREATE PROCEDURE Ins_FeedBack
@AcountId	int ,
@FeedBackTitle	nvarchar(100) ,
@FeedBackContent	ntext ,
@DateCreation	datetime  
AS BEGIN 
     INSERT INTO FeedBack(AcountId,FeedBackTitle,FeedBackContent,DateCreation  ) 
     VALUES (@AcountId,@FeedBackTitle,@FeedBackContent,@DateCreation  ) 
  END 

 GO 

/* Create Procedure Del_FeedBackById */

CREATE PROCEDURE Del_FeedBackById 
@FeedBackId		int 
AS BEGIN 
     DELETE FROM FeedBack
     WHERE FeedBackId=@FeedBackId 
  END 

 GO 

/* Create Procedure Sel_AllComment*/

CREATE PROCEDURE Sel_AllComment
AS BEGIN 
	 SELECT CommentId,AcountId,FeedBackId,CommentContent 
	 FROM Comment
  END 

 GO 

/* Create Procedure Sel_CommentById */

CREATE PROCEDURE Sel_CommentById
@CommentId		int 
AS BEGIN 
	 SELECT CommentId,AcountId,FeedBackId,CommentContent 
	 FROM Comment
	 WHERE CommentId=@CommentId 
  END 

 GO 

/* Create Procedure Udp_CommentById */

CREATE PROCEDURE Udp_CommentById 
@CommentId	int ,
@AcountId	int ,
@FeedBackId	int ,
@CommentContent	ntext  
AS BEGIN 
     UPDATE Comment SET 
	AcountId 	 = @AcountId,
	FeedBackId 	 = @FeedBackId,
	CommentContent 	 = @CommentContent 
    WHERE CommentId=@CommentId 
  END 

 GO 

/* Create Procedure Ins_Comment */

CREATE PROCEDURE Ins_Comment
@AcountId	int ,
@FeedBackId	int ,
@CommentContent	ntext  
AS BEGIN 
     INSERT INTO Comment(AcountId,FeedBackId,CommentContent  ) 
     VALUES (@AcountId,@FeedBackId,@CommentContent  ) 
  END 

 GO 

/* Create Procedure Del_CommentById */

CREATE PROCEDURE Del_CommentById 
@CommentId		int 
AS BEGIN 
     DELETE FROM Comment
     WHERE CommentId=@CommentId 
  END 

 GO 

/* Create Procedure Sel_AllNewscategories*/

CREATE PROCEDURE Sel_AllNewscategories
AS BEGIN 
	 SELECT NewsCategoryId,NewsCategoryName,Description 
	 FROM Newscategories
  END 

 GO 

/* Create Procedure Sel_NewscategoriesById */

CREATE PROCEDURE Sel_NewscategoriesById
@NewsCategoryId		int 
AS BEGIN 
	 SELECT NewsCategoryId,NewsCategoryName,Description 
	 FROM Newscategories
	 WHERE NewsCategoryId=@NewsCategoryId 
  END 

 GO 

/* Create Procedure Udp_NewscategoriesById */

CREATE PROCEDURE Udp_NewscategoriesById 
@NewsCategoryId	int ,
@NewsCategoryName	nvarchar(100) ,
@Description	nvarchar(200)  
AS BEGIN 
     UPDATE Newscategories SET 
	NewsCategoryName 	 = @NewsCategoryName,
	Description 	 = @Description 
    WHERE NewsCategoryId=@NewsCategoryId 
  END 

 GO 

/* Create Procedure Ins_Newscategories */

CREATE PROCEDURE Ins_Newscategories
@NewsCategoryName	nvarchar(100) ,
@Description	nvarchar(200)  
AS BEGIN 
     INSERT INTO Newscategories(NewsCategoryName,Description  ) 
     VALUES (@NewsCategoryName,@Description  ) 
  END 

 GO 

/* Create Procedure Del_NewscategoriesById */

CREATE PROCEDURE Del_NewscategoriesById 
@NewsCategoryId		int 
AS BEGIN 
     DELETE FROM Newscategories
     WHERE NewsCategoryId=@NewsCategoryId 
  END 

 GO 

/* Create Procedure Sel_AllNews*/

CREATE PROCEDURE Sel_AllNews
AS BEGIN 
	 SELECT NewsId,NewsCategoryId,Tittle,Picture,SubContent,NewsContent,DateCreation 
	 FROM News
  END 

 GO 

/* Create Procedure Sel_NewsById */

CREATE PROCEDURE Sel_NewsById
@NewsId		int 
AS BEGIN 
	 SELECT NewsId,NewsCategoryId,Tittle,Picture,SubContent,NewsContent,DateCreation 
	 FROM News
	 WHERE NewsId=@NewsId 
  END 

 GO 

/* Create Procedure Udp_NewsById */

CREATE PROCEDURE Udp_NewsById 
@NewsId	int ,
@NewsCategoryId	int ,
@Tittle	nvarchar(100) ,
@Picture	nvarchar(100) ,
@SubContent	nvarchar(150) ,
@NewsContent	ntext ,
@DateCreation	datetime  
AS BEGIN 
     UPDATE News SET 
	NewsCategoryId 	 = @NewsCategoryId,
	Tittle 	 = @Tittle,
	Picture 	 = @Picture,
	SubContent 	 = @SubContent,
	NewsContent 	 = @NewsContent,
	DateCreation 	 = @DateCreation 
    WHERE NewsId=@NewsId 
  END 

 GO 

/* Create Procedure Ins_News */

CREATE PROCEDURE Ins_News
@NewsId	int ,
@NewsCategoryId	int ,
@Tittle	nvarchar(100) ,
@Picture	nvarchar(100) ,
@SubContent	nvarchar(150) ,
@NewsContent	ntext ,
@DateCreation	datetime  
AS BEGIN 
     INSERT INTO News(NewsCategoryId,Tittle,Picture,SubContent,NewsContent,DateCreation  ) 
     VALUES (@NewsCategoryId,@Tittle,@Picture,@SubContent,@NewsContent,@DateCreation  ) 
  END 

 GO 

/* Create Procedure Del_NewsById */

CREATE PROCEDURE Del_NewsById 
@NewsId		int 
AS BEGIN 
     DELETE FROM News
     WHERE NewsId=@NewsId 
  END 

 GO 

EXEC Sel_Students 
CREATE PROCEDURE Sel_Students 
AS BEGIN 
SELECT     Student.StudentId, Account.AcountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Student.ClazzId, Student.FullName, 
                      Student.BirthDay, Student.Gender, Student.Phone, Student.Email, Student.Address
FROM         Account INNER JOIN
                      Student ON Account.AcountId = Student.AcountId
  END

CREATE PROCEDURE Sel_StudentById 
@StudentId		int
AS BEGIN 
SELECT     Student.StudentId, Account.AcountId, Account.RoleId, Account.UserName, Account.PassWord, Account.DateCreation, Student.ClazzId, Student.FullName, 
                      Student.BirthDay, Student.Gender, Student.Phone, Student.Email, Student.Address
FROM         Account INNER JOIN
                      Student ON Account.AcountId = Student.AcountId
	 WHERE StudentId=@StudentId 
  END