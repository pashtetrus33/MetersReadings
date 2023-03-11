<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">Сброс пароля</h4>
    <hr>
    <form action="/registration" method="post" style="margin: 0 auto; width: 60%">
        <div class="form-group">
            <label for="address" style="margin: auto;">Электронная почта</label>
            <input type="email" class="form-control" id="email" required name="email">
        </div>
        <div style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-danger mb-2">Сбросить пароль</button>
        </div>

    </form>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mb-2" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <#if errorMessage??>
        <h6 style="color: red">${errorMessage}</h6>
    </#if>
</@t.template>