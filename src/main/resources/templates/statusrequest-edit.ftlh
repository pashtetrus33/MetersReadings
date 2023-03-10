<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h3 style="text-align: center">Редактирование заявки пользователя:</h3>
    <h3 style="text-align: center"> ${user.name}</h3>
    <h3 style="text-align: center"> Заявка: ${user.getFirstRequest().description}</h3>
    <h3 style="text-align: center"> Дата создания:  ${user.getFirstRequest().dateOfCreated}</h3>

    <hr>
    <hr>
    <form action="/request/statusedit" method="post">
        <div>
            <label for="role" style="margin: auto;">Изменить статус заявки</label><br>
            <@enumStatus "status" statuses/>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark" style="margin: 10px;">Сохранить</button>
    </form>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark" style="margin: 10px;"
           href="/allusersrequests" role="button">
            Назад
        </a>
    </div>

<#-- handy macro for this -->
    <#macro enumStatus selectName enumValues>
        <select name="${selectName}">
            <#list enumValues as enum>
                <option value="${enum}">${enum.title}</option>
            </#list>
        </select>
    </#macro>


</@t.template>