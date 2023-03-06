<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <form action="/" method="post">
        <div class="form-group" style="margin: 0 auto; text-align: center; width: 50%">
            <label for="login">Имя пользователя</label>
            <input type="text" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="login"
                   name="username">
            <div id="login" class="invalid-feedback">
                Имя пользователя не найдено
            </div>
        </div>
        <div class="form-group" style="margin: 0 auto; text-align: center; width: 50%">
            <label for="password">Пароль</label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password"
                   name="password">
            <div id="password" class="invalid-feedback">
                Неверный пароль
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style=" margin: 30px auto; width: 50%" class="btn btn-dark">Войти</button>
        </div>
    </form>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a style=" margin: 30px auto; width:25%" class="btn btn-dark"
           href="/registration" role="button">
            Регистрация
        </a>
    </div>
    <#if loginmessage??>
        <h2 style="color: red">${loginmessage}</h2>
    </#if>
    <#if loginalert??>
        <h2 style="color: red">${loginalert}</h2>
    </#if>

</@t.template>