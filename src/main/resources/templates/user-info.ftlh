<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h4 style="text-align: center">Информация о пользователе</h4>
    <hr>
    <div style="text-align: center">
            <img src="/static/images/avatar.jpg"
                 style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <h4>${user.name}</h4>
        <br>
        <b>${user.email}</b>
        <br>
        <b>${user.address}</b>


    </div>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <hr>
    <h4 style="text-align: center">История показаний счетчиков </h4>
    <br>
    <div class="card-columns">
        <#list records as record>
            <a href="/record/${record.id}" class="record-link">
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <h5 class="card-title">Дата создания:  ${record.dateOfCreated}</h5>
                        <p class="card-text"> Холодная вода (кухня):${record.kitchenCold}</p>
                        <p class="card-text"> Горячая вода (кухня):${record.kitchenHot}</p>
                        <p class="card-text"> Холодная вода (ванная):${record.toiletCold}</p>
                        <p class="card-text"> Горячая вода (ванная): ${record.toiletHot}</p>
                    </div>
                    <div class="card-footer">
                        ${record.user.name}
                    </div>
                </div>
            </a>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>

    <br>
    <hr>
    <h4 style="text-align: center">История заявок </h4>
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
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>
</@t.template>