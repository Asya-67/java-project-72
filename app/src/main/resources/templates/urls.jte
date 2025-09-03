@import hexlet.code.dto.UrlsDto
@import hexlet.code.Methods
@param UrlsDto page = null

@@extends("layout/commonsite.jte")
@section content {

    @if(page.getFlash() != null)
        <div class="alert alert-${page.getColor()}">
            ${page.getFlash()}
        </div>
    @endif

    <h1 class="mb-4">Список сайтов</h1>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Последняя проверка</th>
                <th>Статус</th>
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

    <a href="/" class="btn btn-success mt-3">Добавить URL</a>
}
