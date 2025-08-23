@param java.util.List<hexlet.code.Url> urls
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Список сайтов</title>
</head>
<body>
<h1>Сайты</h1>
<ul>
    @for(url : urls)
        <li><a href="/urls/${url.getId()}">${url.getName()}</a></li>
    @endfor
</ul>
<a href="/">Назад</a>
</body>
</html>
