DELETE FROM CPS610_USERS_US;

CALL CPS610_USSP_InsertUser('User1@example.us');
CALL CPS610_USSP_InsertUser('User2@example.us');
CALL CPS610_USSP_InsertUser('User3@example.us');
CALL CPS610_USSP_InsertUser('User4@example.us');
CALL CPS610_USSP_InsertUser('User5@example.us');


DELETE FROM CPS610_Purchases_US;

CALL CPS610_USSP_InsertPurchase('User3@example.us', 'Caught in the Act');
CALL CPS610_USSP_InsertPurchase('User4@example.us', 'Caught in the Act');
CALL CPS610_USSP_InsertPurchase('User4@example.us', 'Nobody but Me');
CALL CPS610_USSP_InsertPurchase('User5@example.us', 'Caught in the Act');
CALL CPS610_USSP_InsertPurchase('User5@example.us', 'Christmas');


COMMIT;

            
DECLARE
    FK_User_ID_ NVARCHAR2(40);

BEGIN

   SELECT PK_User_ID INTO FK_User_ID_
   FROM CPS610_USERS_US
   WHERE Email = 'User5@example.us';           

    UPDATE CPS610_Purchases_US
    SET CreatedAt = (SYSDATE - 370)
    WHERE FK_User_ID = FK_User_ID_ AND
                AlbumTitle = 'Christmas';
                
   COMMIT;
                      
END;  
            

