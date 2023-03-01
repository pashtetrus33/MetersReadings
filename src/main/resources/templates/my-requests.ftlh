<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">История заявок на ремонт</h1>
    <hr>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       href="/profile" role="button">
        Назад на страницу профиля
    </a>
    <br>
    <div class="card-columns">
        <#list requests as request>
            <a href="/request/${request.id}" class="request-link">
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-text"> Адрес: ${user.address}</p>
                        <p class="card-text"> Описание: ${request.description}</p>
                        <p class="card-text"> Статус: ${request.status}</p>
                    </div>
                    <div class="card-footer">
                        ${request.dateOfCreated}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>