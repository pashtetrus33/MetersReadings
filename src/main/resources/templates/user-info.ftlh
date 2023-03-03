<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h1 style="text-align: center">Информация о пользователе</h1>
    <hr>
    <div style="text-align: center">
            <img src="/static/images/avatar.jpg"
                 style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${user.name}</h2>
        <br>
        Адрес: <b>${user.address}</b>

    </div>
    <br>
    <hr>
    <h1 style="text-align: center">История показаний счетчиков </h1>
    <br>
    <div class="card-columns">
        <#list records as record>
            <a href="/record/${record.id}" class="record-link">
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <h5 class="card-title">Дата создания: ${record.dateOfCreated}</h5>
                        <p class="card-text"> Кухня (холодная вода):${record.kitchenCold}</p>
                        <p class="card-text"> Кухня (горячая вода):${record.kitchenHot}</p>
                        <p class="card-text"> Cанузел (холодная вода):${record.toiletCold}</p>
                        <p class="card-text"> Cанузел (горячая вода): ${record.toiletHot}</p>
                    </div>
                    <div class="card-footer">
                        ${record.user.name}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>

    <br>
    <hr>
    <h1 style="text-align: center">История заявок </h1>
    <br>
    <div class="card-columns">
        <#list requests as request>
            <a href="/request/${request.id}" class="request-link">
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-title">Дата создания: <br>${request.dateOfCreated}</p>
                        <p class="card-text"> Адрес: <br>${user.address}</p>
                        <p class="card-text"> Описание: <br>${request.description}</p>
                        <p class="card-text"> Статус: <br>${request.status}</p>

                    </div>
                    <div class="card-footer">
                        Имя пользователя: ${request.user.name}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>