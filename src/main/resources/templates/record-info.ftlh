<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h3 style="text-align: center">${record.dateOfCreated} </h3>
    <p><h3 style="text-align: center">${authorRecord.address} </h3>

    <hr>
    <p style="text-align: center">Xолодная вода (кухня): <b><h4>${record.kitchenCold}</b></h4>
    <p style="text-align: center">Горячая вода (кухня): <b><h4>${record.kitchenHot}</b></h4>
    <p style="text-align: center">Холодная вода (ванная): <b><h4>${record.toiletCold}</b></h4>
    <p style="text-align: center">Горячая вода (ванная): <b><h4>${record.toiletHot}</b></h4>

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