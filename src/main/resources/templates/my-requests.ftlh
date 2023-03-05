<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">История заявок на ремонт</h1>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 25%"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <div class="card-columns">
        <#list requests as request>
            <a href="/request/${request.id}" class="request-link">
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-text"> Адрес: ${user.address}</p>
                        <p class="card-text"> Описание: ${request.description}</p>
                        <p class="card-text"> Статус: <#list request.statuses as status>${status}</#list></p>
                    </div>
                    <div class="card-footer">
                        ${request.dateOfCreated}
                    </div>
                </div>
            </a>
        <#else>
            <h3 style="text-align: center">Не найдено</h3>
        </#list>
    </div>
</@t.template>