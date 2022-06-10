MERGE [dbo].[CLS_LEGAL_FORM] AS target USING (SELECT 1) AS source (CLF_ID)  
ON (target.CLF_ID = source.CLF_ID)  
WHEN MATCHED THEN 
	UPDATE SET CLF_NAME = 'Общества с ограниченной ответственностью'  
WHEN NOT MATCHED THEN  
    INSERT (CLF_ID, CLF_NAME)  
    VALUES (1, 'Общества с ограниченной ответственностью' );
go

MERGE [dbo].[CLS_LEGAL_FORM] AS target USING (SELECT 2) AS source (CLF_ID)  
ON (target.CLF_ID = source.CLF_ID)  
WHEN MATCHED THEN 
	UPDATE SET CLF_NAME = 'Публичные акционерные общества'  
WHEN NOT MATCHED THEN  
    INSERT (CLF_ID, CLF_NAME)  
    VALUES (2, 'Публичные акционерные общества' );
go
