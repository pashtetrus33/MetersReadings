<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h3 style="text-align: center">${record.dateOfCreated} </h3></p>
    <p><h3 style="text-align: center">${authorRecord.address} </h3></p>

    <hr>
    <p style="text-align: center">Xолодная вода (кухня): <b>${record.kitchenCold}</b></p>
    <p style="text-align: center">Горячая вода (кухня): <b>${record.kitchenHot}</b></p>
    <p style="text-align: center">Холодная вода (ванная): <b>${record.toiletCold}</b></p>
    <p style="text-align: center">Горячая вода (ванная): <b>${record.toiletHot}</b></p>

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