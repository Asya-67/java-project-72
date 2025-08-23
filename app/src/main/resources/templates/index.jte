@param String flash
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добавить сайт</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="text-center text-primary">Добавить сайт</h1>

@if(flash != null)
    <div class="alert alert-info">${flash}</div>
@endif

<form action="/urls" method="post" class="mb-3">
    <input type="text" name="url" class="form-control mb-2" placeholder="https://example.com">
    <button type="submit" class="btn btn-primary">Добавить</button>
</form>

<a href="/urls">Список сайтов</a>
</body>
</html>
