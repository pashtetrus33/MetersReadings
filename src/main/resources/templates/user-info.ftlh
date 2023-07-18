<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h4 style="text-align: center">Информация о пользователе</h4>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.jpg"
             style="border:2px solid black; border-radius: 20px" height="120" alt="avatar">
        <br>
        <br>
        <h5><b>${user.name}</b></h5>
        <h5>${user.address}</h5>
        <h5>${user.email}</h5>

    </div>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <hr>
    <br>
    <hr>
    <h4 style="text-align: center">История показаний счетчиков </h4>
    <br>
    <div class="card-columns">
        <#list records as record>
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <h6 class="card-title">Дата создания: ${record.dateOfCreatedString}</h6>
                        <p class="card-text"> Холодная вода (кухня):${record.kitchenCold}</p>
                        <p class="card-text"> Горячая вода (кухня):${record.kitchenHot}</p>
                        <p class="card-text"> Холодная вода (ванная):${record.toiletCold}</p>
                        <p class="card-text"> Горячая вода (ванная): ${record.toiletHot}</p>
                    </div>
                    <div class="card-footer">
                        ${record.user.name}
                    </div>
                    <div>
                        <#if userByPrincipal.isAdmin()>
                            <form action="/record/delete/${record.id?c}" method="post"
                                  style="margin: 0 auto; text-align: center">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                                    Удалить запись
                                </button>
                            </form>
                        </#if>
                    </div>
                </div>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>

    <br>
    <hr>
    <h4 style="text-align: center">История показаний счетчиков электричества </h4>
    <br>
    <div class="card-columns">
        <#list electricityRecords as record>
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <h6 class="card-title">Дата создания: ${record.dateOfCreatedString}</h6>
                        <p class="card-text"> Показание: ${record.getElectricity()}</p>
                        <p class="card-text"> Данные внес: ${record.dataProviderName}</p>
                    </div>
                    <div class="card-footer">
                        ${record.user.name}
                    </div>
                    <div>
                        <#if userByPrincipal.isAdmin()>
                            <form action="/electricityrecord/delete/${record.id?c}" method="post"
                                  style="margin: 0 auto; text-align: center">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                                    Удалить запись
                                </button>
                            </form>
                        </#if>
                    </div>
                </div>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>
    <h4 style="text-align: center">История заявок </h4>
    <br>
    <div class="card-columns">
        <#list requests as request>
                <div class="card text-white bg-dark my-3 request">

                    <div class="card-body">
                        <p class="card-title">Дата создания: <br>${request.dateOfCreatedString}</p>
                        <p class="card-text"> Адрес: <br>${user.address}</p>
                        <p class="card-text"> Описание: <br>${request.description}</p>
                        <p class="card-text"> Фото: <br><#list request.getImages() as image>
                            <a href="/images/${image.id}">Ссылка</a></#list></p>
                        <p class="card-text"> Статус: <br>${request.status}</p>
                        <p class="card-text"> Исполнитель: <br>${request.executor}</p>
                    </div>
                    <div class="card-footer">
                        ${request.user.name}
                    </div>
                    <div>
                        <#if userByPrincipal.isAdmin()>
                            <form action="/request/delete/${request.id?c}" method="post"
                                  style="margin: 0 auto; text-align: center">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                                    Удалить запись
                                </button>
                            </form>
                        </#if>
                    </div>
                </div>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>
</@t.template>