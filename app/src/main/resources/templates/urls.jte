@import hexlet.code.AppPaths
@params List<hexlet.code.models.Url> urls
@params String flash

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h1>Список URL</h1>

@if(flash != null && !flash.isEmpty())
    <div class="alert alert-success">@flash</div>
@endif

<table class="table table-bordered">
    <thead>
        <tr>
            <th>ID</th>
            <th>URL</th>
            <th>Последняя проверка</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
    </thead>
    <tbody>
        @for(url in urls)
            <tr>
                <td>@url.getId()</td>
                <td>@url.getName()</td>
                <td>
                    @if(url.getChecks() != null && !url.getChecks().isEmpty())
                        @url.getChecks().get(url.getChecks().size() - 1).getCreatedAt()
                    @else
                        -
                    @endif
                </td>
                <td>
                    @if(url.getChecks() != null && !url.getChecks().isEmpty())
                        @url.getChecks().get(url.getChecks().size() - 1).getStatusCode()
                    @else
                        -
                    @endif
                </td>
                <td>
                    <a href="${AppPaths.url(url.getId())}" class="btn btn-sm btn-info">Посмотреть</a>
                    <form action="${AppPaths.urlChecks(url.getId())}" method="POST" style="display:inline">
                        <button type="submit" class="btn btn-sm btn-primary">Проверить</button>
                    </form>
                </td>
            </tr>
        @endfor
    </tbody>
</table>

</body>
</html>
