DELETE FROM CPS610_ALBUMS_HQ;

CALL CPS610_HQSP_InsertAlbum('Come Fly with Me', 15.25, NULL);
CALL CPS610_HQSP_InsertAlbum('Caught in the Act', 16.50, 13.10);
CALL CPS610_HQSP_InsertAlbum('Nobody but Me', NULL, 12.50);
CALL CPS610_HQSP_InsertAlbum('To Be Loved', 13.75, NULL);
CALL CPS610_HQSP_InsertAlbum('Christmas', 25.50, 20.25);

DELETE FROM CPS610_Purchases_HQ_Archive;

COMMIT;