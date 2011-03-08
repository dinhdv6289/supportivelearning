create database SupportiveLearning
GO

use SupportiveLearning
GO

create table Class
(
	ClassId			 INT IDENTITY(1,1) PRIMARY KEY,
	ClassName		 NVARCHAR(100),
	Sem				 NVARCHAR(100),
	DateStart		 DATETIME DEFAULT GETDATE(),
	DateEnd			 DATETIME		
)
GO

create table Newscategories
(
	NewsCategoryId   INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	NewsCategoryName NVARCHAR(100),
	Description NVARCHAR(200)
)
GO

create table News
(
	NewsId           INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	NewsCategoryId	 INT REFERENCES Newscategories(NewsCategoryId) NOT NULL,	
	Tittle			 NVARCHAR(100),
	Picture			 NVARCHAR(100),
	Subconten		 NVARCHAR(150),
	[Content]		 NTEXT,
	DateCreate		 DATETIME DEFAULT GETDATE()
)
GO


create table ManagerAssignment
(
	ManagerAssId     INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	NameAssignment	 NVARCHAR(100),
	Mark		     FLOAT
)
GO


create table [Role]
(
	RoleId           INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	Description      NVARCHAR(100)
)
GO

create table Account
(
	AcountId         INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	RoleId           INT REFERENCES [Role](RoleId) NOT NULL,
	UserName         NVARCHAR(100),
	PassWord         NVARCHAR(100),
	DateCreate       DATETIME DEFAULT GETDATE()
)
GO

create table Student
(
	StudentId        INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	RoleId           INT REFERENCES [Role](RoleId) NOT NULL,
	ClassId			 INT REFERENCES Class(ClassId) NOT NULL,	
	FullName         NVARCHAR(200) NOT NULL,
	BirthDay         DATETIME,
	Gender           BIT DEFAULT 1,
	Phone            NVARCHAR(100),
	Email            NVARCHAR(100) NOT NULL,
	Address          NVARCHAR(255)
)
GO


create table Assignment
(
	AssignmentId     INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	StudentId        INT REFERENCES Student(StudentId) NOT NULL,
	ManagerAssId     INT REFERENCES ManagerAssignment(ManagerAssId) NOT NULL,
	NameAssignment   NVARCHAR(100),
	FileUpload       NVARCHAR(255),
	Mark             FLOAT,
	DateUpload       DATETIME DEFAULT GETDATE()
)

