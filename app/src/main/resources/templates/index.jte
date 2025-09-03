@import hexlet.code.dto.Base
@param Base page = null

@@extends("layout/commonsite.jte")
@section content {
    <h1 class="mb-4">Добавьте URL</h1>

    @if(page != null && page.getFlash() != null)
        <div class="alert alert-${page.getColor()} alert-dismissible fade show" role="alert">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif

    <form method="post" action="/urls">
        <div class="mb-3">
            <input type="text" name="url" class="form-control" placeholder="Введите URL" required>
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
}
