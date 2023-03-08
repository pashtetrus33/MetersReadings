<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <form action="/" method="post" style="width: 25%">
        <div class="form-group" style="text-align: center">
            <label for="email" style="margin: auto;">Электронная почта</label>
            <input type="email" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email"
                   name="username">
            <div id="email" class="invalid-feedback">
                Электронная почта не найдена
            </div>
        </div>
        <div class="form-group" style="margin: 0 auto; text-align: center">
            <label for="password" style="margin: auto;">Пароль</label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password"
                   name="password">
            <div id="password" class="invalid-feedback">
                Неверный пароль
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style=" margin: 15px auto" class="btn btn-dark">Войти</button>
        </div>
    </form>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a style=" margin: auto; width:auto" class="btn btn-dark"
           href="/registration" role="button">
            Регистрация
        </a>
    </div>
    <#if loginmessage??>
        <h6 style="color: red">${loginmessage}</h6>
    </#if>
    <#if loginalert??>
        <h6 style="color: red">${loginalert}</h6>
    </#if>

</@t.template>