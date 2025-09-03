@import hexlet.code.dto.UrlDto
@import hexlet.code.Methods
@param UrlDto page = null

@@extends("layout/commonsite.jte")
@section content {

    @if(page.getFlash() != null)
        <div class="alert alert-${page.getColor()}">
            ${page.getFlash()}
        </div>
    @endif

    <h1 class="mb-4">Страница: ${page.getUrl().getName()}</h1>

    <div class="mb-3">
        <a href="/urls" class="btn btn-secondary">Назад к списку URL</a>
        <a href="/urls/${page.getUrl().getId()}/checks" class="btn btn-primary ms-2">Проверить сайт</a>
    </div>

    <table class="table table-bordered table-hover mt-3">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Status Code</th>
                <th>Title</th>
                <th>H1</th>
                <th>Description</th>
                <th>Дата проверки</th>
            </tr>
        </thead>
        <tbody>
            @if(page.getChecks() != null && !page.getChecks().isEmpty())
                @for(var check : page.getChecks())
                    <tr>
                        <td>${check.getId()}</td>
                        <td>${check.getStatusCode()}</td>
                        <td>${check.getTitle()}</td>
                        <td>${check.getH1()}</td>
                        <td>${check.getDescription()}</td>
                        <td>${Methods.timeFormat(check.getCreatedAt())}</td>
                    </tr>
                @endfor
            @else
                <tr>
                    <td colspan="6" class="text-center">Проверки отсутствуют</td>
                </tr>
            @endif
        </tbody>
    </table>
}
