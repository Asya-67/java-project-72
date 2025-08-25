<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <h1>Добавленные URL</h1>
    <table class="table table-striped mt-3">
        <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Дата добавления</th>
            </tr>
        </thead>
        <tbody>
            @for(url : urls)
            <tr>
                <td>@url.getId()</td>
                <td>@url.getName()</td>
                <td>@url.getCreatedAt()</td>
            </tr>
            @end
        </tbody>
    </table>
    <a href="/" class="btn btn-primary mt-3">Добавить новый URL</a>
</body>
</html>
