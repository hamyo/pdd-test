MERGE [dbo].[SYS_PROPS] AS target USING (SELECT 'MaxFailedAttempts') AS source (SP_NAME)  
ON (target.SP_NAME = source.SP_NAME)  
WHEN MATCHED THEN 
	UPDATE SET SP_VALUE = 4  
WHEN NOT MATCHED THEN  
    INSERT (SP_NAME, SP_VALUE)  
    VALUES ('MaxFailedAttempts', 4);
go

