<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <h1>Добавьте URL</h1>
    <form action="/urls" method="POST">
        <input type="text" name="url" class="form-control mb-2" placeholder="https://example.com">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
</body>
</html>
