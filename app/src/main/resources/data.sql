INSERT INTO urls (name) VALUES ('https://example.com');
INSERT INTO urls (name) VALUES ('https://hexlet.io');
INSERT INTO urls (name) VALUES ('https://google.com');

INSERT INTO url_checks (url_id, status_code, title, h1, description) VALUES
  (1, 200, 'Example Domain', 'Example Domain', 'This domain is for use in illustrative examples in documents.');

INSERT INTO url_checks (url_id, status_code, title, h1, description) VALUES
  (2, 200, 'Hexlet – онлайн-школа программирования', 'Учитесь программировать', 'Курсы программирования для начинающих и продвинутых.');

INSERT INTO url_checks (url_id, status_code, title, h1, description) VALUES
  (3, 301, 'Google', 'Google', 'Поиск информации в интернете.');
