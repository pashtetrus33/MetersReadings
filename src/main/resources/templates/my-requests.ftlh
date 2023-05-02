<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">История заявок на ремонт</h4>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <div class="card-columns">
        <#list requests as request>
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-text"> Адрес: ${user.address}</p>
                        <p class="card-text"> Описание: ${request.description}</p>
                        <p class="card-text">Фото: <br><#list request.getImages() as image><a href="/images/${image.id}">
                                Ссылка</a></#list></p>
                        <p class="card-text"> Статус: ${request.status}</p>
                        <p class="card-text"> Исполнитель: ${request.executor}</p>
                    </div>
                    <div class="card-footer">
                        ${request.dateOfCreatedString}
                    </div>
                </div>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>
</@t.template>