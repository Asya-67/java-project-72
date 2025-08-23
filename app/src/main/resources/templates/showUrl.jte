@param hexlet.code.Url url
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сайт ${url.getName()}</title>
</head>
<body>
<h1>Сайт: ${url.getName()}</h1>
<p>ID: ${url.getId()}</p>
<p>Дата добавления: ${url.getCreatedAt()}</p>
<a href="/urls">Назад</a>
</body>
</html>
