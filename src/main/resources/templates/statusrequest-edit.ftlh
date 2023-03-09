<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h3 style="text-align: center">Редактирование заявки пользователя:</h3>
    <h3 style="text-align: center"> ${user.name}</h3>
    <h3 style="text-align: center"> Заявка: ${user.getFirstRequest().description}</h3>
    <h3 style="text-align: center"> Дата создания:  ${user.getFirstRequest().dateOfCreated}</h3>

    <hr>
    <form action="/request/statusedit" method="post">
        <#list statuses as status>
            <div>
                <label><input type="checkbox"
                              name="${status}" ${user.getFirstRequest().statuses?seq_contains(status)?string("checked", "")}>${status}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.getFirstRequest().id}" name="requestId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark">Сохранить</button>
    </form>

    <#if user.isAdmin()>
        <form action="/request/delete/${id}" method="post" style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="width: 50%" class="btn btn-danger mb-2">
                Удалить заявку
            </button>
        </form>
    </#if>
</@t.template>