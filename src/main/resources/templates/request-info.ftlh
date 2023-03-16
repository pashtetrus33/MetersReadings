<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h5 style="text-align: center">${authorRequest.name} </h5>
    <p><h5 style="text-align: center">${request.dateOfCreatedString} </h5>
    <p><h5 style="text-align: center">${authorRequest.address} </h5>
    <hr>
    <p style="text-align: center; width: 100%; word-break: break-all; table-layout: fixed">Описание: <b>${request.description}</b></p>
    <p style="text-align: center">Статус: <b>${request.status}</b></p>
    <p style="text-align: center">Исполнитель: <b>${request.executor}</b></p>

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