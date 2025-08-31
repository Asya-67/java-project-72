@import hexlet.code.Methods
@import hexlet.code.AppPaths
@content commonsite(content, page) {
    <div class="container mt-5">
        <h1>Список сайтов</h1>
        <ul class="list-group">
            {{ for(var entry : page.getUrlsWithLastChecks().entrySet()) }}
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="/urls/{{ entry.getKey().getId() }}">{{ entry.getKey().getName() }}</a>
                    @if(entry.getValue() != null)
                        <span class="badge bg-success">Последняя проверка: {{ Methods.timeFormat(entry.getValue().getCreatedAt()) }}</span>
                    @endif
                </li>
            {{ end }}
        </ul>
    </div>
}
