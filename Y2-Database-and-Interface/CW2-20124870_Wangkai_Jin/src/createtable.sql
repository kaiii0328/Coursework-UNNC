USE scywj1;
CREATE TABLE Manager(
	Username varchar(25) primary key,
    Password varchar(25)
);
CREATE TABLE Customer(
	UserName varchar(25) primary key,
    Telephone bigint unique,    
    Password  varchar(20),
    RealName varchar(25),
    Region varchar(25),
    PassportID varchar(20) unique,
    Email  varchar(20) unique
 );
  CREATE TABLE SalesRepresentative(
	UserName varchar(25) primary key,    
    Password  varchar(20),
    RealName varchar(25),
    EmployeeID varchar(20) unique,
    Telephone bigint unique,
    Email  varchar(20) unique,
    Quota int(10) null,
    Region varchar(25) null,
    ManagerName  varchar(25),
    constraint managerNameref
		foreign key	(ManagerName)
        REFERENCES Manager (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE
 );
 CREATE TABLE OrderingInfo(
	OrderID integer(10) primary key,
    Masktype varchar(25),
    Quantity integer(6),
    Amount integer(7),
    Time date,
    Status integer(10),
    RepName varchar(25),
    CusName varchar(25),
    constraint repNameref
		foreign key	(RepName)
        REFERENCES SalesRepresentative (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE,
	constraint cusNameref
		foreign key	(CusName)
        REFERENCES Customer (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE
 );
 CREATE TABLE Admin(
	Username varchar(25) primary key,
    Password varchar(25)
 );
 CREATE TABLE Admin_SalesRepresentative(
	AdminUsername  varchar(25),
    SalesRepresentativeUserName varchar(25),
     constraint adNameref
		foreign key	(AdminUsername)
        REFERENCES Admin (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE,
	 constraint salesrepNameref
		foreign key	(SalesRepresentativeUserName)
        REFERENCES SalesRepresentative (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE
 );
  CREATE TABLE Admin_Manager(
	AdminUsername  varchar(25),
    ManagerUserName varchar(25),
     constraint admNameref
		foreign key	(AdminUsername)
        REFERENCES Admin (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE,
	 constraint managNameref
		foreign key	(ManagerUserName)
        REFERENCES Manager (UserName)
			ON DELETE SET NULL
            ON UPDATE CASCADE
 );