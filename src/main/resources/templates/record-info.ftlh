<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h5 style="text-align: center">${authorRecord.name} </h5>
    <p><h5 style="text-align: center">${record.dateOfCreated} </h5>
    <p><h5 style="text-align: center">${authorRecord.address} </h5>
    <hr>
    <p style="text-align: center">Xолодная вода (кухня): <h5>${record.kitchenCold}</h5>
    <p style="text-align: center">Горячая вода (кухня): <h5>${record.kitchenHot}</h5>
    <p style="text-align: center">Холодная вода (ванная): <h5>${record.toiletCold}</h5>
    <p style="text-align: center">Горячая вода (ванная): <h5>${record.toiletHot}</h5>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/user/${user.id}" role="button">
            Назад
        </a>
    </div>

    <#if user.isAdmin()>
        <form action="/record/delete/${id}" method="post" style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                Удалить запись
            </button>
        </form>
    </#if>

</@t.template>