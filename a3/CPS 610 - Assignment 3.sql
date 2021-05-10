/* 
    Assignment 3
    CPS 610 – Database Systems II - Winter 2021
    Wednesday Section – Group 13
    Member #1: Andy Lee (500163559)
    Member #2: Sohrab Soltani (500801172)
*/


/* 
    Question 1
    Create Professor1 Table to have an address included as an object.
    See example below which adds address as an object (user defined type) to a table called addresses.
*/
DROP TYPE CPS610_Address_Typ;

CREATE TYPE CPS610_Address_Typ AS OBJECT
(
    StreetNo NUMBER(10),
    StreetName NVARCHAR2(100),
    AptNo NUMBER(5),
    City NVARCHAR2(100),
    State NVARCHAR2(100),
    ZipCode NVARCHAR2(9),
    Country NVARCHAR2(100)
);




DROP TABLE CPS610_Addresses; 

CREATE TABLE CPS610_Addresses OF CPS610_Address_Typ;

INSERT INTO CPS610_Addresses
VALUES(681, 'Goldsworth', 25, 'New Orleans', 'Louisiana', 'H4Y 5T8', 'USA');

INSERT INTO CPS610_Addresses
VALUES(18, 'Market St', 32, 'Toronto', 'Ontario', 'J7X 2P9', 'Canada');

SELECT REF(e) FROM CPS610_Addresses e;

SELECT VALUE(f) FROM CPS610_Addresses f;



DROP TABLE CPS610_Professor1; 

CREATE TABLE CPS610_Professor1
(
    PK_Professor_ID           NVARCHAR2(32)  DEFAULT SYS_GUID(),
    FirstName                    NVARCHAR2(100),
    LastName                    NVARCHAR2(100),
    Address                       CPS610_Address_Typ
);

INSERT INTO CPS610_Professor1
(FirstName,
 LastName,
 Address)
 VALUES
('John',
 'Smith',
  CPS610_Address_Typ(681, 'Goldsworth', 25, 'New Orleans', 'Louisiana', 'H4Y 5T8', 'USA'));
  
INSERT INTO CPS610_Professor1
(FirstName,
 LastName,
 Address)
 VALUES
('Jane',
 'Doe',
  CPS610_Address_Typ(18, 'Market St', 32, 'Toronto', 'Ontario', 'J7X 2P9', 'Canada'));
  
  
SELECT * FROM CPS610_Professor1;

SELECT     PK_Professor_ID,
                 FirstName, 
                 LastName,
                 P1.Address.StreetNo,
                 P1.Address.StreetName,
                 P1.Address.AptNo,
                 P1.Address.City,
                 P1.Address.State,
                 P1.Address.ZipCode,
                 P1.Address.Country
                 FROM CPS610_Professor1 P1;

/* 
    End of Question 1
*/




/* 
    Question 2
    Create Professor2 Table to contain a circular object type
    in a way that is similar to MARRIEDPERSON TABLE shown below.
*/


DROP TYPE CPS610_Professor2_Type; 

CREATE TYPE CPS610_Professor2_Type AS OBJECT
(
    FirstName                    NVARCHAR2(100),
    LastName                    NVARCHAR2(100),
    Supervisor                   REF CPS610_Professor2_Type
);


DROP TABLE CPS610_Professor2; 

CREATE TABLE CPS610_Professor2 OF CPS610_Professor2_Type;



INSERT INTO CPS610_Professor2
(FirstName,
 LastName)
 VALUES
('Jane',
 'Smith');
  
INSERT INTO CPS610_Professor2
SELECT
 'John',
 'Doe',
 REF(Sup) FROM CPS610_Professor2 Sup WHERE FirstName = 'Jane';
 
 
INSERT INTO CPS610_Professor2
SELECT
 'Bridget',
 'Jones',
 REF(Sup) FROM CPS610_Professor2 Sup WHERE FirstName = 'John';
  
  
SELECT * FROM CPS610_Professor2;

SELECT     FirstName, 
                 LastName,
                 P2.Supervisor.FirstName AS SupervisorFirstName,
                 P2.Supervisor.LastName AS SupervisorLastName
                 FROM CPS610_Professor2 P2;

/* 
    End of Question 2
*/



/* 
    Question 3
    Based on your observations explain what is REF

    
    Answer: Our understanding is that REF allows us to have either columns in a table
                 to be defined of a user-defined type or to define attributes within a 
                 user-defined type to be defined as user-defined types. It acts like a link
                 between the column/attribute to a custom datatype. Much like a pointer
                 in structures we define in the C language or classes in object-oriented
                 programming languages.
    

    End of Question 3
*/



/* 
    Question 4
    Add an attribute to show the number courses a professor is teaching
    in the professor object and then use PL/SQL and write a procedure 
    to increase the number of courses a professor is teaching.
*/

    
DROP TYPE CPS610_Professor4_Type FORCE; 

CREATE TYPE CPS610_Professor4_Type AS OBJECT
(
    PK_Professor_ID           NVARCHAR2(32),
    FirstName                    NVARCHAR2(100),
    LastName                    NVARCHAR2(100),
    Address                       CPS610_Address_Typ,
    Supervisor                   REF CPS610_Professor4_Type,
    NumberOfCourses        NUMBER
);


DROP TABLE CPS610_Professor4_Table; 

CREATE TABLE CPS610_Professor4_Table OF CPS610_Professor4_Type;

-- Add first professor
INSERT INTO CPS610_Professor4_Table
(
    PK_Professor_ID,
    FirstName,
    LastName,
    Address,
    NumberOfCourses
)
VALUES
(
 'CC5B9E488B134C579FD541334C26112C',
 'Bridget',
 'Jones',
 CPS610_Address_Typ(681, 'Goldsworth', 25, 'New Orleans', 'Louisiana', 'H4Y 5T8', 'USA'),
 0);
 
INSERT INTO CPS610_Professor4_Table
SELECT
 'C77288E04A21443E9310280086B9BE7A',
 'John',
 'Doe',
 CPS610_Address_Typ(18, 'Market St', 32, 'Toronto', 'Ontario', 'J7X 2P9', 'Canada'),
 REF(Sup),
 0
 FROM CPS610_Professor4_Table Sup
 WHERE PK_Professor_ID = 'CC5B9E488B134C579FD541334C26112C';
 
 
SELECT * FROM CPS610_Professor4_Table;

SELECT     PK_Professor_ID,
                 FirstName, 
                 LastName,
                 P4.Address.StreetNo,
                 P4.Address.StreetName,
                 P4.Address.AptNo,
                 P4.Address.City,
                 P4.Address.State,
                 P4.Address.ZipCode,
                 P4.Address.Country,
                 P4.Supervisor.FirstName AS SupervisorFirstName,
                 P4.Supervisor.LastName AS SupervisorLastName,
                 NumberOfCourses
                 FROM CPS610_Professor4_Table P4;
 


-- Increase Professor Jone's courses by 2
DECLARE
  CurrentNumberOfCourses  NUMBER;
BEGIN

  SELECT NumberOfCourses INTO CurrentNumberOfCourses FROM CPS610_Professor4_Table  WHERE LastName = 'Jones';
 
  UPDATE CPS610_Professor4_Table
  SET NumberOfCourses = (CurrentNumberOfCourses + 2)
  WHERE LastName = 'Jones';
 
END;

-- Increase Professor Doe's courses by 1
DECLARE
  CurrentNumberOfCourses  NUMBER;
BEGIN

  SELECT NumberOfCourses INTO CurrentNumberOfCourses FROM CPS610_Professor4_Table  WHERE LastName = 'Doe';
 
  UPDATE CPS610_Professor4_Table
  SET NumberOfCourses = (CurrentNumberOfCourses + 1)
  WHERE LastName = 'Doe';
 
END;


-- Move one course from professor Jones to professor Doe
DECLARE
  CurrentNumberOfCourses_Jones  NUMBER;
BEGIN

  SELECT NumberOfCourses INTO CurrentNumberOfCourses_Jones FROM CPS610_Professor4_Table  WHERE LastName = 'Jones';
    
    DECLARE
        CurrentNumberOfCourses_Doe  NUMBER;
    BEGIN
    
      SELECT NumberOfCourses INTO CurrentNumberOfCourses_Doe FROM CPS610_Professor4_Table  WHERE LastName = 'Doe';
     
      UPDATE CPS610_Professor4_Table
      SET NumberOfCourses = (CurrentNumberOfCourses_Jones - 1)
      WHERE LastName = 'Jones';
      
      UPDATE CPS610_Professor4_Table
      SET NumberOfCourses = (CurrentNumberOfCourses_Doe + 1)
      WHERE LastName = 'Doe';
      
    END;  
    
END;


    
/*
    End of Question 4
*/

