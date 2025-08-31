@import hexlet.code.dto.UrlDto
@import hexlet.code.models.UrlCheck
@param UrlDto page = null

@extends("layout.jte")
@section content {
    <div class="container mt-4">
        <h1>Страница: ${page.getUrl().getName()}</h1>

        <table class="table table-bordered mt-3">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Status Code</th>
                    <th>Title</th>
                    <th>H1</th>
                    <th>Description</th>
                    <th>Created At</th>
                </tr>
            </thead>
            <tbody>
                @if(page.getChecks() != null && !page.getChecks().isEmpty())
                    @for(hexlet.code.models.UrlCheck check : page.getChecks())
                        <tr>
                            <td>${check.getId()}</td>
                            <td>${check.getStatusCode()}</td>
                            <td>${check.getTitle()}</td>
                            <td>${check.getH1()}</td>
                            <td>${check.getDescription()}</td>
                            <td>${check.getCreatedAt().toLocalDateTime().toString()}</td>
                        </tr>
                    @endfor
                @else
                    <tr>
                        <td colspan="6" class="text-center">Проверки отсутствуют</td>
                    </tr>
                @endif
            </tbody>
        </table>

        <a href="/urls" class="btn btn-secondary mt-3">Назад к списку URL</a>
    </div>
}
