<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h4 style="text-align: center">Информация о пользователе</h4>
    <hr>
    <div style="text-align: center">
            <img src="/static/images/avatar.jpg"
                 style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h4>${user.name}</h4>
        <br>
        <b>${user.address}</b>
        <b>${user.email}</b>

    </div>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 25%"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <hr>
    <h3 style="text-align: center">История показаний счетчиков </h3>
    <br>
    <div class="card-columns">
        <#list records as record>
            <a href="/record/${record.id}" class="record-link">
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <h5 class="card-title">Дата создания:  ${record.dateOfCreated}</h5>
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
            <h3 style="text-align: center">Не найдено</h3>
        </#list>
    </div>

    <br>
    <hr>
    <h3 style="text-align: center">История заявок </h3>
    <br>
    <div class="card-columns">
        <#list requests as request>
            <a href="/request/${request.id}" class="request-link">
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-title">Дата создания: <br>${request.dateOfCreated}</p>
                        <p class="card-text"> Адрес: <br>${user.address}</p>
                        <p class="card-text"> Описание: <br>${request.description}</p>
                        <p class="card-text"> Статус: <br><#list request.statuses as status>${status}</#list></p>

                    </div>
                    <div class="card-footer">
                        ${request.user.name}
                    </div>
                </div>
            </a>
        <#else>
            <h3 style="text-align: center">Не найдено</h3>
        </#list>
    </div>
</@t.template>