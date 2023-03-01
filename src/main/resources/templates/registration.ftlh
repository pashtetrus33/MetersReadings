<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Регистрация</h1>
    <hr>
    <form action="/registration" method="post">
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
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Зарегистрироваться</button>
    </form>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       href="/profile" role="button">
        Назад на страницу профиля
    </a>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@t.template>