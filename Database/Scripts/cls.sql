INSERT INTO cls_role (cr_id, cr_name)
VALUES
    (1, 'Администратор'),
    (2, 'Пользователь')
ON CONFLICT (cr_id) DO UPDATE
    SET cr_name = excluded.cr_name;

INSERT INTO cls_question_type (cqt_id, cqt_name)
VALUES
    (1, 'Вопрос с одним доступным ответом на выбор')
ON CONFLICT (cqt_id) DO UPDATE
    SET cqt_name = excluded.cqt_name;
