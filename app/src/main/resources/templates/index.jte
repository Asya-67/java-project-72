@import hexlet.code.dto.Base
@param Base page = null

@@extends("layout/commonsite.jte")
@section content {
    <h1 class="mb-4">Добавьте URL</h1>

    <form method="post" action="/urls">
        <div class="mb-3">
            <input type="text" name="url" class="form-control" placeholder="Введите URL" required>
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
}
