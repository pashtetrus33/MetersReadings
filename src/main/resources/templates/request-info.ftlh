<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h3 style="text-align: center">${request.dateOfCreated} </h3></p>
    <p><h3 style="text-align: center">${authorRequest.address} </h3></p>

    <hr>
    <p style="text-align: center">Описание: <b>${request.description}</b></p>
    <p style="text-align: center">Статус: <b><#list request.statuses as status>${status}</#list></b></p>

    <#list images as img>
        <a href="/images/${img.id}"><img src="/images/${img.id} " height="300px"/></a>
    </#list>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 50%"
           href="/user/${user.id}" role="button">
            Назад
        </a>
    </div>

    <#if user.isAdmin()>
             <form action="/request/delete/${id}" method="post" style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-danger mb-2">
                Удалить заявку
            </button>
        </form>
    </#if>

</@t.template>