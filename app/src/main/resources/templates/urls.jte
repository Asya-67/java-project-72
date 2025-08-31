@import hexlet.code.dto.Base
@import hexlet.code.models.Url
@import hexlet.code.models.UrlCheck
@content urls(Base page) {
    @commonsite(content, page) {
        <div class="container mt-5">
            <h1>Сайты</h1>
            <ul class="list-group">
                {{ for (var entry : page.getUrlsWithLastChecks().entrySet()) { }}
                    var url = entry.getKey();
                    var lastCheck = entry.getValue();
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <a href="/urls/${url.getId()}">${url.getName()}</a>
                        @if(lastCheck != null)
                            <span class="badge bg-secondary rounded-pill">${lastCheck.getStatusCode()}</span>
                        @endif
                    </li>
                {{ } }}
            </ul>
        </div>
    }
}
