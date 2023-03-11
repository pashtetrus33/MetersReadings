<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h4 style="text-align: center">${request.dateOfCreated} </h4>
    <p><h4 style="text-align: center">${authorRequest.address} </h4>

    <hr>
    <p style="text-align: center; width: 100%; word-break: break-all; table-layout: fixed">Описание: <b>${request.description}</b></p>
    <p style="text-align: center">Статус: <b><#list request.statuses as status>${status}</#list></b></p>

    <#list images as img>
        <a href="/images/${img.id}"><img src="/images/${img.id} " height="300px"/></a>
    </#list>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/user/${user.id}" role="button">
            Назад
        </a>
    </div>
</@t.template>