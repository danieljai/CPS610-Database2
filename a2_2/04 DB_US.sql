DROP DATABASE LINK DB_HQ;

CREATE DATABASE LINK DB_HQ 
CONNECT TO Server1 IDENTIFIED BY password 
USING '192.168.25.100:1521/xe';



DROP TABLE CPS610_USERS_US;

CREATE TABLE CPS610_USERS_US
(
    PK_User_ID            NVARCHAR2(40) DEFAULT CONCAT('US_', SYS_GUID()),   
    
    Email                     NVARCHAR2(25) NOT NULL,
    
    CONSTRAINT USERS_US_ID  PRIMARY KEY(PK_User_ID),
    UNIQUE(Email)
);




DROP TABLE CPS610_Purchases_US;

CREATE TABLE CPS610_Purchases_US
(
    PK_Purchase_ID           NVARCHAR2(40) DEFAULT CONCAT('US_', SYS_GUID()),    
    
    FK_User_ID                 NVARCHAR2(40) NOT NULL REFERENCES CPS610_USERS_US (PK_User_ID) ON DELETE CASCADE,    
    
    AlbumTitle                   NVARCHAR2(100) NOT NULL,
    
    Price                           FLOAT NULL,   
    
    CreatedAt                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT PURCHASES_US_ID  PRIMARY KEY(PK_Purchase_ID)    
);



CREATE  OR REPLACE  VIEW CPS610_ALBUMS_US
 AS
(
    SELECT PK_Album_ID, AlbumTitle, USPrice AS Price
    FROM CPS610_ALBUMS_HQ@DB_HQ
    WHERE USPrice IS NOT NULL
);


CREATE OR REPLACE PROCEDURE CPS610_USSP_InsertUser
(
  Email_ IN NVARCHAR2
) AS 
BEGIN
    
    LOCK TABLE CPS610_USERS_US IN EXCLUSIVE MODE;
    
    INSERT INTO CPS610_USERS_US
    (Email)
    VALUES
    (Email_);
    
END CPS610_USSP_InsertUser;






CREATE OR REPLACE PROCEDURE CPS610_USSP_InsertPurchase
(
  Email_ IN NVARCHAR2,
  AlbumTitle_ IN NVARCHAR2
) AS 
BEGIN
    
    LOCK TABLE CPS610_Purchases_US IN EXCLUSIVE MODE;
    LOCK TABLE CPS610_USERS_US IN EXCLUSIVE MODE;
    
    
    DECLARE
        PK_User_ID_ NVARCHAR2(40);

    BEGIN
       SELECT PK_User_ID INTO PK_User_ID_
       FROM CPS610_USERS_US
       WHERE Email = Email_;       
       
        DECLARE
            Price_ FLOAT;
    
        BEGIN
           SELECT Price INTO Price_
           FROM CPS610_ALBUMS_US
           WHERE AlbumTitle = AlbumTitle_;           
           
                INSERT INTO CPS610_Purchases_US
                (FK_User_ID,
                 AlbumTitle,
                 Price)
                VALUES
                (PK_User_ID_,
                 AlbumTitle_,
                 Price_);
                      
        END;       
       
    END;    
    
END CPS610_USSP_InsertPurchase;




