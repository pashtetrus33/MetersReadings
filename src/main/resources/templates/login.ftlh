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
            <button type="submit" style=" margin: 15px auto; width: auto" class="btn btn-dark">Войти</button>
        </div>
    </form>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a style=" margin: 0px auto; width:auto" class="btn btn-dark"
           href="/registration" role="button">
            Регистрация
        </a>
    </div>
    <#if loginmessage??>
        <h4 style="color: red">${loginmessage}</h4>
    </#if>
    <#if loginalert??>
        <h4 style="color: red">${loginalert}</h4>
    </#if>

</@t.template>