DROP DATABASE LINK DB_CAN;

CREATE DATABASE LINK DB_CAN 
CONNECT TO Server2 IDENTIFIED BY password 
USING '192.168.25.100:1521/xe';



DROP DATABASE LINK DB_US;

CREATE DATABASE LINK DB_US 
CONNECT TO Server3 IDENTIFIED BY password 
USING '192.168.25.100:1521/xe';




DROP TABLE CPS610_ALBUMS_HQ;

CREATE TABLE CPS610_ALBUMS_HQ
(
    
    PK_Album_ID           NVARCHAR2(32)  DEFAULT SYS_GUID(),
    
    AlbumTitle               NVARCHAR2(100) NOT NULL,
    
    CanPrice                  FLOAT NULL,      
    USPrice                    FLOAT NULL,      
    
    CONSTRAINT ALBUMS_HQ_ID  PRIMARY KEY(PK_Album_ID)
    
);





CREATE OR REPLACE VIEW CPS610_Purchases_HQ
 AS
(

    SELECT     PK_Purchase_ID, FK_User_ID, AlbumTitle , Price AS CanPrice, NULL AS USPrice, CreatedAt
    FROM CPS610_Purchases_CAN@DB_CAN
    UNION
    SELECT     PK_Purchase_ID, FK_User_ID, AlbumTitle , NULL AS CanPrice, Price AS USPrice, CreatedAt
    FROM CPS610_Purchases_US@DB_US
);



CREATE OR REPLACE VIEW CPS610_Users_HQ
 AS
(

    SELECT     PK_User_ID, Email, 'Canada' AS Country
    FROM CPS610_USERS_CAN@DB_CAN
    UNION
    SELECT     PK_User_ID, Email, 'US' AS Country
    FROM CPS610_USERS_US@DB_US
);



CREATE OR REPLACE PROCEDURE CPS610_HQSP_InsertAlbum
(
  AlbumTitle_ IN NVARCHAR2,
  CanPrice_ IN FLOAT,
  USPrice_ IN FLOAT
) AS 
BEGIN
    
    LOCK TABLE CPS610_ALBUMS_HQ IN EXCLUSIVE MODE;
    
    INSERT INTO CPS610_ALBUMS_HQ
    (AlbumTitle, CanPrice, USPrice)
    VALUES
    (AlbumTitle_, CanPrice_, USPrice_);
    
END CPS610_HQSP_InsertAlbum;



DROP TABLE CPS610_Purchases_HQ_Archive;

CREATE TABLE CPS610_Purchases_HQ_Archive
(
    PK_Purchase_ID          NVARCHAR2(40),   
    
    FK_User_ID                 NVARCHAR2(40) NOT NULL,    
    
    AlbumTitle                   NVARCHAR2(100) NOT NULL,
    
    Price                           FLOAT NULL,
    
    CreatedAt                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT PURCHASES_Archive_ID  PRIMARY KEY(PK_Purchase_ID)    
);


