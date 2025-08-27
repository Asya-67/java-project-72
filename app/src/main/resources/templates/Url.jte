@import hexlet.code.AppPaths
@params hexlet.code.models.Url url
@params String flash

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Информация о URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h1>URL: @url.getName()</h1>

@if(flash != null && !flash.isEmpty())
    <div class="alert alert-success">@flash</div>
@endif

<div class="mb-3">
    <strong>ID:</strong> @url.getId()
</div>

<h3>Проверки</h3>
@if(url.getChecks() != null && !url.getChecks().isEmpty())
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Дата</th>
                <th>Статус</th>
                <th>Title</th>
                <th>H1</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
        @for(check in url.getChecks())
            <tr>
                <td>@Methods.timeFormat(check.getCreatedAt())</td>
                <td>@check.getStatusCode()</td>
                <td>@(check.getTitle() != null && !check.getTitle().isEmpty() ? check.getTitle() : "-")</td>
                <td>@(check.getH1() != null && !check.getH1().isEmpty() ? check.getH1() : "-")</td>
                <td>@(check.getDescription() != null && !check.getDescription().isEmpty() ? check.getDescription() : "-")</td>
            </tr>
        @endfor
        </tbody>
    </table>
@else
    <p>Нет проверок</p>
@endif

<form action="${AppPaths.urlChecks(url.getId())}" method="POST">
    <button type="submit" class="btn btn-primary">Проверить URL</button>
</form>

<div class="mt-3">
    <a href="${AppPaths.urls()}" class="btn btn-secondary">Назад к списку URL</a>
</div>

</body>
</html>
