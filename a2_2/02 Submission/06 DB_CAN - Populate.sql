DELETE FROM CPS610_USERS_CAN;

CALL CPS610_CANSP_InsertUser('User1@example.ca');
CALL CPS610_CANSP_InsertUser('User2@example.ca');
CALL CPS610_CANSP_InsertUser('User3@example.ca');
CALL CPS610_CANSP_InsertUser('User4@example.ca');
CALL CPS610_CANSP_InsertUser('User5@example.ca');


DELETE FROM CPS610_Purchases_CAN;

CALL CPS610_CANSP_InsertPurchase('User1@example.ca', 'Come Fly with Me');
CALL CPS610_CANSP_InsertPurchase('User2@example.ca', 'Come Fly with Me');
CALL CPS610_CANSP_InsertPurchase('User2@example.ca', 'Caught in the Act');
CALL CPS610_CANSP_InsertPurchase('User3@example.ca', 'To Be Loved');
CALL CPS610_CANSP_InsertPurchase('User3@example.ca', 'Christmas');



COMMIT;


DECLARE
    FK_User_ID_ NVARCHAR2(40);

BEGIN
   SELECT PK_User_ID INTO FK_User_ID_
   FROM CPS610_USERS_CAN
   WHERE Email = 'User3@example.ca';           

    UPDATE CPS610_Purchases_CAN
    SET CreatedAt = (SYSDATE - 370)
    WHERE FK_User_ID = FK_User_ID_ AND
                AlbumTitle = 'Christmas';
                
    COMMIT;
                      
END; 