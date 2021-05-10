SET SERVEROUTPUT ON;
DECLARE
    TheMoment TIMESTAMP;
BEGIN
    
    SELECT (SYSDATE - 365) INTO TheMoment FROM DUAL;
    
    DECLARE
        TheCount_CAN INT;
    BEGIN
        
        SELECT COUNT(*) INTO TheCount_CAN
        FROM CPS610_Purchases_CAN@DB_CAN
        WHERE CreatedAt <= TheMoment;
        
        IF (TheCount_CAN = 0) THEN
        
            dbms_output.put_line('The were no old purchase records in the Canadian store DB');
            
        ELSE
        
            dbms_output.put_line('Found old purchase records in the Canadian store DB');
            
            INSERT INTO CPS610_Purchases_HQ_Archive
            (PK_Purchase_ID,
             FK_User_ID,
             AlbumTitle,
             Price,
             CreatedAt)
             SELECT PK_Purchase_ID,
             FK_User_ID,
             AlbumTitle,
             Price,
             CreatedAt
             FROM CPS610_Purchases_CAN@DB_CAN
             WHERE CreatedAt <= TheMoment;
             
             dbms_output.put_line('Old purchase records from the Canadian store DB were copied to the headquarters DB');
             
             DELETE FROM CPS610_Purchases_CAN@DB_CAN
             WHERE CreatedAt <= TheMoment;
             
             dbms_output.put_line('Old purchase records were deleted from the Canadian store DB ');
             
              COMMIT;
        
        END IF; 
    
    END;
    

    
    DECLARE
        TheCount_US INT;
    BEGIN
        
        SELECT COUNT(*) INTO TheCount_US
        FROM CPS610_Purchases_US@DB_US
        WHERE CreatedAt <= TheMoment;
        
        IF (TheCount_US = 0) THEN
        
            dbms_output.put_line('The were no old purchase records in the US store DB');
            
        ELSE
        
            dbms_output.put_line('Found old purchase records in the US store DB');
            
            INSERT INTO CPS610_Purchases_HQ_Archive
            (PK_Purchase_ID,
             FK_User_ID,
             AlbumTitle,
             Price,
             CreatedAt)
             SELECT PK_Purchase_ID,
             FK_User_ID,
             AlbumTitle,
             Price,
             CreatedAt
             FROM CPS610_Purchases_US@DB_US
             WHERE CreatedAt <= TheMoment;
             
             dbms_output.put_line('Old purchase records from the US store DB were copied to the headquarters DB');
             
             DELETE FROM CPS610_Purchases_US@DB_US
             WHERE CreatedAt <= TheMoment;
             
             dbms_output.put_line('Old purchase records were deleted from the US store DB ');
             
             COMMIT;
        
        END IF; 
    
    END;


END;