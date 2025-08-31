@import hexlet.code.dto.UrlsDto
@param UrlsDto page = null

@extends("layout.jte")
@section content {
    <div class="container mt-4">
        <h1>Список сайтов</h1>
        <table class="table table-bordered mt-3">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>URL</th>
                    <th>Последняя проверка</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                @for(url, check : page.getUrlsWithLastChecks().entrySet())
                    <tr>
                        <td>${url.getId()}</td>
                        <td>${url.getName()}</td>
                        <td>
                            @if(check != null)
                                ${check.getCreatedAt()}
                            @else
                                Нет проверок
                            @endif
                        </td>
                        <td>
                            <a href="/urls/${url.getId()}" class="btn btn-sm btn-primary">Посмотреть</a>
                        </td>
                    </tr>
                @end
            </tbody>
        </table>
    </div>
}

