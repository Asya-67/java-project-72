@import hexlet.code.Methods
@import hexlet.code.AppPaths
@content commonsite(content, page) {
    <div class="container mt-5">
        <h1>{{ url.getName() }}</h1>
        <p>Добавлен: {{ Methods.timeFormat(url.getCreatedAt()) }}</p>

        <h2>Проверки</h2>
        <ul class="list-group">
            {{ for(var check : checks) }}
                <li class="list-group-item">
                    Дата: {{ Methods.timeFormat(check.getCreatedAt()) }},
                    Статус: {{ check.getStatus() }}
                    <a href="{{ AppPaths.urlChecksPath(url.getId()) }}" class="btn btn-sm btn-primary ms-2">Проверить снова</a>
                </li>
            {{ end }}
        </ul>
    </div>
}
