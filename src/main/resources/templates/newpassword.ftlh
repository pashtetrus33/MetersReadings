<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">Установка нового пароля</h4>
    <hr>
    <form action="/admin/user/password_change" method="post" style="text-align: center">
        <label for="password">Изменить пароль</label>
        <input type="password" class="form-control" id="password" required name="password"
               style="width: auto; margin: auto">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark" style="margin: 13px;">Сохранить</button>
    </form>
</@t.template>