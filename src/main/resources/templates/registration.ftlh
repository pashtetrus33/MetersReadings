<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Регистрация</h1>
    <hr>
    <form action="/registration" method="post" style="margin: 0 auto; text-align: center; width: 50%">
        <div class="form-group">
            <label for="name">ФИО пользователя</label>
            <input type="text" class="form-control" required id="name" name="name">
        </div>
        <div class="form-group">
            <label for="login">Логин</label>
            <input type="login" class="form-control" id="login" required name="login">
        </div>
        <div class="form-group">
            <label for="address">Адрес пользователя</label>
            <input type="text" class="form-control" id="address" required name="address">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" required name="password">
        </div>
        <div style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 50%" class="btn btn-danger mb-2">Зарегистрировать</button>
        </div>

    </form>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mb-2" style="text-align: center; width: 25%"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@t.template>