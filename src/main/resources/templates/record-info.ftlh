<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <p><h2 style="text-align: center">${record.dateOfCreated} </h2></p>
    <p><h2 style="text-align: center">${authorRecord.address} </h2></p>

    <hr>
    <p style="text-align: center">Кухня холодная вода: <b>${record.kitchenCold}</b></p>
    <p style="text-align: center">Кухня горячая вода: <b>${record.kitchenHot}</b></p>
    <p style="text-align: center">Санузел холодная вода: <b>${record.toiletCold}</b></p>
    <p style="text-align: center">Санузел горячая вода: <b>${record.toiletHot}</b></p>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 50%"
           href="/profile" role="button">
            Назад
        </a>
    </div>

    <#if user.isAdmin()>
        <form action="/record/delete/${id}" method="post" style="margin: 0 auto; text-align: center">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 50%" class="btn btn-danger mb-2">
                Удалить запись
            </button>
        </form>
    </#if>

</@t.template>