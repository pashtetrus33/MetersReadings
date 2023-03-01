<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h2 style="text-align: center">${request.dateOfCreated} </h2></p>
    <p><h2 style="text-align: center">${authorRequest.address} </h2></p>

    <hr>
    <p style="text-align: center">Описание: <b>${request.description}</b></p>
    <p style="text-align: center">Статус: <b>${request.status}</b></p>

    <#list images as img>
        <a href="/images/${img.id}"><img src="/images/${img.id} " height="300px"/></a>
    </#list>

    <#if user.isAdmin()>
        <form action="/request/status/${id}" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
                Изменить статус
            </button>
        </form>
        <form action="/request/delete/${id}" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
                Удалить заявку
            </button>
        </form>
    </#if>

</@t.template>