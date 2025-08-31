@import hexlet.code.dto.Base
@import hexlet.code.Methods
@content url(Base page) {
    @commonsite(content, page) {
        <div class="container mt-5">
            <h1>${page.getUrl().getName()}</h1>
            <p>Добавлен: ${Methods.timeFormat(page.getUrl().getCreatedAt())}</p>

            <h2>Проверки</h2>
            <ul class="list-group">
                {{ for (var check : page.getUrlChecks()) { }}
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span>
                            Дата: ${Methods.timeFormat(check.getCreatedAt())},
                            Статус: ${check.getStatusCode()}
                        </span>
                        <a href="/urls/${page.getUrl().getId()}/checks" class="btn btn-sm btn-primary ms-2">Проверить снова</a>
                    </li>
                {{ } }}
            </ul>
        </div>
    }
}
