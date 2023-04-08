<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Панель администратора</h4>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mb-2" style="text-align: center; width: auto"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-danger mb-2" style="text-align: center; width: auto"
           href="/registration" role="button">
            Регистрация
        </a>
    </div>
    <hr>

    <form action="/admin/sendmail" method="post" style="margin: 0 auto; width: 60%">
        <div class="form-group">
            <label for="email" style="margin: auto;">Электронная почта</label>
            <input type="email" class="form-control" id="email" required name="email" placeholder="all@all.ru письмо всем">
        </div>
        <div class="form-group">
            <label for="message" style="margin: auto;">Сообщение</label>
            <textarea class="form-control" id="message" rows="4" name="message"></textarea>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button type="submit" class="btn btn-dark" style="margin: 5px">Отправить письмо</button>
    </form>
    <hr>
    <#if loginalert??>
        <h6 style="color: red">${loginalert}</h6>
    </#if>
    <br>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Имя</th>
            <th scope="col">Эл.почта</th>
            <th scope="col">Адрес</th>
            <th scope="col">Активность</th>
            <th scope="col">Роли</th>
            <th scope="col">Редактирование</th>
            <th scope="col">Подробная информация</th>

        </tr>
        <#list users as user>
            <tr>
                <th>${user.name}</th>
                <th>${user.email}</th>
                <th>${user.address}</th>
                <th><#if user.active>true<#else>false</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th><a href="/admin/user/edit/${user.id?c}">Редактировние</a></th>
                <th><a href="/user/${user.id?c}">Подробная информация</a></th>
            </tr>
        <#else>
            <h3>Пользователей нет</h3>
        </#list>
    </table>
</@t.template>