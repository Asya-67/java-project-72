@import hexlet.code.dto.UrlsDto
@import hexlet.code.Methods
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
                    <th>Статус</th> <!-- новая колонка -->
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                @for(var entry : page.getUrlsWithLastChecks().entrySet())
                    <tr>
                        <td>${entry.getKey().getId()}</td>
                        <td>${entry.getKey().getName()}</td>
                        <td>
                            @if(entry.getValue() != null)
                                ${Methods.timeFormat(entry.getValue().getCreatedAt())}
                            @else
                                Нет проверок
                            @endif
                        </td>
                        <td>
                            @if(entry.getValue() != null)
                                ${entry.getValue().getStatusCode()}
                            @else
                                -
                            @endif
                        </td>
                        <td>
                            <a href="/urls/${entry.getKey().getId()}" class="btn btn-sm btn-primary">Посмотреть</a>
                        </td>
                    </tr>
                @endfor
            </tbody>
        </table>
    </div>
}
