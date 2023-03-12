<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">Установка нового пароля</h4>
    <hr>
    <form action="/newpassword" method="post" style="margin: 0 auto; width: 60%">
        <div class="form-group">
            <label for="address" style="margin: auto;">Новый пароль</label>
            <input type="password" class="form-control" id="password" required name="password">
        </div>
        <div style="margin: 0 auto; text-align: center">
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-danger mb-2">Установить</button>
        </div>

    </form>
</@t.template>